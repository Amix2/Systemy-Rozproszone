package exceptions;

import akka.actor.ActorRef;

public class BookNotInDBException extends Exception{
	public ActorRef actor;
	public String name;

	public BookNotInDBException() {
	}

	public BookNotInDBException(ActorRef actor, String name) {
		this.actor = actor;
		this.name = name;
	}
}
