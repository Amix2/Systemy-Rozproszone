package messages;

import java.io.Serializable;

import akka.actor.ActorRef;

@SuppressWarnings("serial")
public class StreamRequest implements Serializable {
	public String name;
	public ActorRef actor;

	public StreamRequest(String name, ActorRef actor) {
		this.name = name;
		this.actor = actor;
	}
}
