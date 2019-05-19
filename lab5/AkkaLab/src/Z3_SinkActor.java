import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z3_SinkActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, o -> {
                        log.info("! msg: " + o.toString());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}