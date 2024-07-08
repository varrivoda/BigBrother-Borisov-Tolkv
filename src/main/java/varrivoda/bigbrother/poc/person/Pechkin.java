package varrivoda.bigbrother.poc.person;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import varrivoda.bigbrother.poc.PocHelper;
import varrivoda.bigbrother.poc.model.SuspiciousEmail;

public class Pechkin {
    public Flux<SuspiciousEmail> letters() {
        return Flux.<SuspiciousEmail>generate(synchronousSink -> {
            synchronousSink.next(new SuspiciousEmail());
        })
                .publishOn(Schedulers.fromExecutor(PocHelper.executor("pechkin-", 1)))
                .doOnNext(suspiciousEmail -> PocHelper.COUNTER.incrementAndGet())
                .log("pechkin-");
    }
}
