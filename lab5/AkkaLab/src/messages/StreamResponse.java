package messages;

import akka.actor.ActorRef;

public class StreamResponse extends StreamRequest {
	public String line;

	public StreamResponse(String name, ActorRef actor, String line) {
		super(name, actor);
		this.line = line;
	}
}
