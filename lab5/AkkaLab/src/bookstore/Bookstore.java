package bookstore;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import exceptions.BookFileNotAvaliableException;
import exceptions.BookNotInDBException;
import messages.*;
import scala.concurrent.duration.Duration;

public class Bookstore extends AbstractActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    
    public static void main(String[] args) throws Exception {
    	// config
    	File configFile = new File("bookstore_conf.conf");
    	Config config = ConfigFactory.parseFile(configFile);
    	
    	// create actor system & actors
    	final ActorSystem system = ActorSystem.create("bookstore_system", config);
    	system.actorOf(Props.create(Bookstore.class), "bookstore");
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String line = br.readLine();
    	
    	system.terminate();
    }
    
    

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
        		.match(String.class, o -> {
                    log.info(("String msg: " + o.toString()));
                })
        		.match(PriceRequest.class, o -> {
        			context().child("dataBase").get().tell(o, getSelf());
        		})
				.match(OrderRequest.class, o -> {
					context().child("orders").get().tell(o, getSelf());
				})
				.match(StreamRequest.class, o -> {
					context().child("stream").get().tell(o, getSelf());
				})
                .matchAny(o -> log.info("Not-String mgs: " + o.toString()))
                .build();
	}
    
    @Override
    public void preStart() throws Exception {
    	context().actorOf(Props.create(ManagerDB.class), "dataBase");
    	context().actorOf(Props.create(StreamService.class), "stream");
    	context().actorOf(Props.create(Orders.class), "orders");
    	
    }
    
    private static SupervisorStrategy strategy
		= new AllForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
	        .matchAny(o -> restart()).
	        build());

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
    
 
}
