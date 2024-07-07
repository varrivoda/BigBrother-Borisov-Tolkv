package varrivoda.bigbrother.poc.person;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import varrivoda.bigbrother.poc.model.AnalyzedEmail;
import varrivoda.bigbrother.poc.model.SuspiciousEmail;

import java.util.concurrent.TimeUnit;

public class BigBrother {
    public Flux<AnalyzedEmail> analyze(Flux<SuspiciousEmail> suspiciousEmailFlux) {
        return suspiciousEmailFlux
                .log("bigbrother-")
                .map(suspiciousEmail -> new AnalyzedEmail())
                .doOnNext(analyzedEmail -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
