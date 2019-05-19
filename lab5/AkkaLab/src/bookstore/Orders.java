package bookstore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import messages.OrderRequest;
import messages.OrderResponse;

public class Orders extends AbstractActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private final String orderFile = "orders.txt";

	@Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(String.class, o -> {
                    log.info(("String msg: " + o.toString()));
                })
                .match(OrderRequest.class, o -> {
                	boolean done = true;
                	try {
                		makeOrder(o.name);
                	} catch (IOException e) {
                		done = false;
                	}
                	o.actor.tell(new OrderResponse(o.name, o.actor, done), getSelf());
                })
                .matchAny(o -> log.info("Not-String mgs: " + o.toString()))
                .build();
	}
	
	private void makeOrder(String bookName) throws IOException {
		FileWriter fw = new FileWriter(orderFile, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(bookName);
	    bw.newLine();
	    bw.close();    
	}

}
