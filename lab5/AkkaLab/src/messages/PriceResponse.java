package messages;

import akka.actor.ActorRef;

public class PriceResponse extends PriceRequest  {
	public Integer price;

	public PriceResponse(Integer price, ActorRef actor, String name) {
		super(name, actor);
		this.price = price;
	}

}
