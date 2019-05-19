import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        String path = "akka.tcp://remote_system@127.0.0.1:3552/user/remote";
        return receiveBuilder()
                .match(String.class, o -> {
                    if(o.toUpperCase().equals(o)) {
                        log.info("local msg in Upper: " + o.toString());
                    } else {
                        log.info("local msg: " + o.toString());
                        getContext().actorSelection(path).tell(o, getSelf());
                    }

                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
