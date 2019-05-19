import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_RemoteActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        String localPath = "akka.tcp://local_system@127.0.0.1:2552/user/local";
        return receiveBuilder()
                .match(String.class, o -> {
                    //log.info(getSender().toString());
                    log.info(("remote msg: " + o.toString()));
                    getSender().tell(o.toString().toUpperCase(), getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
