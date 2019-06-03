package bookstore;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import exceptions.*;
import messages.PriceResponse;
import scala.concurrent.duration.Duration;
import messages.PriceRequest;
import akka.actor.ActorRef;
import akka.actor.AllForOneStrategy;
import akka.actor.OneForOneStrategy;

public class ManagerDB extends AbstractActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private final static int NUMBER_OF_FILES = 2;
	static Map<String, Integer> requestComplited = new HashMap<>();
	
	private List<ActorRef> dbActors = new ArrayList<ActorRef>();

	@Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(String.class, o -> {
                    log.info(("String msg: " + o.toString()));
                })
                .match(PriceResponse.class, o -> {
                	log.info("price response for :" + o.name);
                	o.actor.tell(o, getSelf());
                })
                .match(PriceRequest.class, o -> {
        			log.info("price test for :" + o.name);
        			for(ActorRef actor : this.dbActors) {
        				actor.tell(o, getSelf());
        			}
        			requestComplited.put(o.name, 0);
        		})
                .match(PriceResponse.class, o -> {
        			log.info("price response for :" + o.name);
        			int endedBefore = requestComplited.get(o.name);
        			if(endedBefore == 0) {
        				o.actor.tell(o, getSelf());
        				requestComplited.put(o.name, endedBefore+1);
        			} 
        			if(endedBefore+1 <= NUMBER_OF_FILES) {
        				requestComplited.remove(o.name);
        			}
        		})
                .matchAny(o -> log.info("Not-String mgs: " + o.toString()))
                .build();
	}
	
	@Override
    public void preStart() throws Exception {
		for(Integer i=0; i<NUMBER_OF_FILES; i++) {
			String fileName = "db"+i.toString()+".txt";
			dbActors.add(context().actorOf(BookDB.props(fileName), "dataBaseFile"+i.toString()));   				
		}
    }
	
	private static SupervisorStrategy strategy
    	= new AllForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
    		.match(IOException.class, o -> restart())
    		.match(BookFileNotAvaliableException.class, o -> {
    			int endedBefore = requestComplited.get(o.name);
    			if(endedBefore+1 <= NUMBER_OF_FILES) {
    				o.actor.tell(new PriceResponse(null, o.actor, o.name), null);
    				requestComplited.remove(o.name);
    			}
    			return resume();
    			})
    		.match(BookNotInDBException.class, o -> {
    			int endedBefore = requestComplited.get(o.name);
    			if(endedBefore+1 <= NUMBER_OF_FILES) {
    				o.actor.tell(new PriceResponse(-1, o.actor, o.name), null);
    				requestComplited.remove(o.name);
    			}
    			return resume();
    			})
            .matchAny(o -> restart()).
            build());

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

}
