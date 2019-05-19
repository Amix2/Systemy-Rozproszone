package messages;

import java.io.Serializable;

import akka.actor.ActorRef;

@SuppressWarnings("serial")
public class OrderRequest implements Serializable {
	public String name;
	public ActorRef actor;

	public OrderRequest(String name, ActorRef actor) {
		this.name = name;
		this.actor = actor;
	}
}
