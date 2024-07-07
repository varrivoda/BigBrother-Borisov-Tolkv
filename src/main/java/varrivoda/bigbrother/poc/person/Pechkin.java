package varrivoda.bigbrother.poc.person;

import reactor.core.publisher.Flux;
import varrivoda.bigbrother.poc.model.SuspiciousEmail;

public class Pechkin {
    public Flux<SuspiciousEmail> letters() {
        return Flux.<SuspiciousEmail>generate(synchronousSink -> {
            synchronousSink.next(new SuspiciousEmail());
        })
                .log("pechkin-");
    }
}
