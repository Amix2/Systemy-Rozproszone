package bookstore;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import messages.StreamRequest;
import messages.StreamResponse;
import scala.concurrent.duration.Duration;

public class StreamService extends AbstractActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	@SuppressWarnings("unchecked")
	@Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(String.class, o -> {
                    log.info(("String msg: " + o.toString()));
                })
                .match(StreamRequest.class, o -> {
					log.info("stream for " + o.name);
					List<String> lineList = new ArrayList<String>();
					for(int i=0; i<10; i++) {
						lineList.add(o.name + " text: " + getAlphaNumericString(10));
					}
					
					final Materializer materializer = ActorMaterializer.create(getContext().system());
					final Source<StreamResponse, NotUsed> source = Source.from(lineList).map(line -> new StreamResponse(o.name, o.actor, (String) line));
					final Sink<StreamResponse, NotUsed> sinkActor2 = Sink.actorRef(o.actor, null);
					source.throttle(1, Duration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping()).runWith(sinkActor2, materializer);
				})
                .matchAny(o -> log.info("Not-String mgs: " + o.toString()))
                .build();
	}
	
	static String getAlphaNumericString(int n) 
    { 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"; 

        StringBuilder sb = new StringBuilder(n); 
        for (int i = 0; i < n; i++) { 
            int index = (int)(AlphaNumericString.length()*Math.random()); 
            sb.append(AlphaNumericString.charAt(index)); 
        } 
 
        return sb.toString(); 
    } 

}
