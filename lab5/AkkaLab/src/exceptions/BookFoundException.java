package exceptions;

import akka.actor.ActorRef;

@SuppressWarnings("serial")
public class BookFoundException extends Exception {
	
	public String name;
	public Integer price;
	public ActorRef actor;
	
	public BookFoundException(Integer price, ActorRef actor, String name) {
		this.price = price;
		this.actor = actor;
		this.name = name;
	}
}
