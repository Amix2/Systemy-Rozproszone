package bookstore;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import exceptions.*;
import messages.PriceResponse;
import messages.PriceRequest;

public class BookDB extends AbstractActor {
		
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	String fileName = "";
	
	 static Props props(String fileName) {
	    return Props.create(BookDB.class, () -> new BookDB(fileName));
	}
	
	 public BookDB(String fileName) {
		 this.fileName = fileName;
	 }

	@Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(String.class, o -> {
                    log.info(("String msg: " + o.toString()));
                })
                .match(PriceRequest.class, o -> {
        			log.info("price test for :" + o.name);
        			try {
	        			Integer price = null;
	        			String name = o.name;
	        			if(name.equals("all")) {
	        				name = allFileInfo();
	        			} else {
	        				price = searchFile(name);
	        				if(price == null) throw new BookNotInDBException(o.actor);
	        			}
	        			
	        			this.getSender().tell(new PriceResponse(price, o.actor, name), getSelf());
	        			
        			} catch(IOException e) {
        				throw new BookFileNotAvaliableException(o.actor);
        			}
        		})
                .matchAny(o -> log.info("Not-String mgs: " + o.toString()))
                .build();
	}
	
	private Integer searchFile(String bookName) throws IOException, BookNotInDBException {
		File file = new File(fileName);  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String line; 
		while ((line = br.readLine()) != null) {
			String[] entry = line.split(":");
			if(entry[0].equals(bookName)) {
				br.close();
				return Integer.parseInt(entry[1]);
			}
		}
		br.close();
		return null;
	}
	
	private String allFileInfo() throws IOException {
		File file = new File(fileName); 
		System.out.println(file.getAbsolutePath());	  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		String out = "";
		  
		String line; 
		while ((line = br.readLine()) != null) {
			out += "->" + line + "\n";
		}
		br.close();
		return out;
	}
}
