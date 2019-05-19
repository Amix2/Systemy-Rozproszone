package main;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import bookstore.Bookstore;
import bookstore.ManagerDB;
import bookstore.Orders;
import bookstore.StreamService;
import messages.*;

public class MainActor extends AbstractActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private final String bookStoreAdress = "akka.tcp://bookstore_system@127.0.0.1:3552/user/bookstore";

	@Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(String.class, o -> {
                    log.info(("String msg: " + o.toString()));
                })
                .match(String[].class, commands -> {
                	log.info(commands.toString());
                	switch(commands[0]) {
            		case "bookList":
            			getContext().actorSelection(bookStoreAdress).tell(new PriceRequest("all",getSelf()), getSelf());
            			break;
            		case "getPrice":
            			getContext().actorSelection(bookStoreAdress).tell(new PriceRequest(commands[1],getSelf()), getSelf());
            			break;
            		case "stream":
            			getContext().actorSelection(bookStoreAdress).tell(new StreamRequest(commands[1], getSelf()), getSelf());
            			break;
            		case "order":
            			getContext().actorSelection(bookStoreAdress).tell(new OrderRequest(commands[1], getSelf()), getSelf());
            			break;
            		}
                })
                .match(PriceResponse.class, o -> {
                	//log.info("price response for :" + o.name);
                	if(o.price!=null) {
                		System.out.println(String.format("price for %s is %d", o.name, o.price));
                	}
                	else {
                		System.out.println("book list\n" + o.name);
                	}
                })
                .match(StreamResponse.class, o -> {
                	//log.info("price response for :" + o.name);
                	System.out.println(o.line);
                	
                })
                .match(OrderResponse.class, o -> {
                	log.info("price response for :" + o.name);
                	if(o.status) {
                		System.out.println("Order succesfull");
                	} else {
                		System.out.println("Order failed");
                	}
                })
                .matchAny(o -> log.info("Not-String mgs: " + o.toString()))
                .build();
	}
	
	@Override
    public void preStart() throws Exception {
	//	context().actorOf(Props.create(Bookstore.class), "bookstore");
    }
}
