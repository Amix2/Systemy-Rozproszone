package exceptions;

import akka.actor.ActorRef;

public class BookFileNotAvaliableException extends Exception{
	public ActorRef actor;
	public String name;

	public BookFileNotAvaliableException() {
	}

	public BookFileNotAvaliableException(ActorRef actor, String name2) {
		this.actor = actor;
		this.name = name2;
	}
}
