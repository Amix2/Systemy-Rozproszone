package exceptions;

import akka.actor.ActorRef;

public class BookNotInDBException extends Exception{
	public ActorRef actor;

	public BookNotInDBException() {
	}

	public BookNotInDBException(ActorRef actor) {
		this.actor = actor;
	}
}
