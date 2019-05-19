package messages;

import akka.actor.ActorRef;

public class OrderResponse extends OrderRequest {

	public boolean status;
	
	public OrderResponse(String name, ActorRef actor, boolean status) {
		super(name, actor);
		this.status = status;
	}

}
