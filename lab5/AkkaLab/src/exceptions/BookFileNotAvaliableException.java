package exceptions;

import akka.actor.ActorRef;

public class BookFileNotAvaliableException extends Exception{
	ActorRef actor;

	public BookFileNotAvaliableException() {
	}

	public BookFileNotAvaliableException(ActorRef actor) {
		this.actor = actor;
	}
}
