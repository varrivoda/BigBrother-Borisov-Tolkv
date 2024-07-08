package varrivoda.bigbrother.poc.person;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import varrivoda.bigbrother.poc.PocHelper;
import varrivoda.bigbrother.poc.model.AnalyzedEmail;
import varrivoda.bigbrother.poc.model.SuspiciousEmail;

import java.util.concurrent.TimeUnit;

public class BigBrother {
    public Flux<AnalyzedEmail> analyze(Flux<SuspiciousEmail> suspiciousEmailFlux) {
        return suspiciousEmailFlux
                .publishOn(Schedulers.fromExecutor(PocHelper.executor("bigbro-", 4)),false, 32)
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
