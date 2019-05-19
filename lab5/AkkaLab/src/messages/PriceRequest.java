package messages;

import java.io.Serializable;

import akka.actor.ActorRef;

@SuppressWarnings("serial")
public class PriceRequest implements Serializable {
	public String name;
	public ActorRef actor;

	public PriceRequest(String name, ActorRef actor) {
		this.name = name;
		this.actor = actor;
	}

}
