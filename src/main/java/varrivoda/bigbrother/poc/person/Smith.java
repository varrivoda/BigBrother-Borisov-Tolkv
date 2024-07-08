package varrivoda.bigbrother.poc.person;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import varrivoda.bigbrother.poc.PocHelper;
import varrivoda.bigbrother.poc.model.AnalyzedEmail;
import varrivoda.bigbrother.poc.model.ReactionForEmail;

import java.util.concurrent.TimeUnit;

public class Smith {
    public Flux<ReactionForEmail> reactFor(Flux<AnalyzedEmail> analyzedEmailFlux) {
        return analyzedEmailFlux
                .publishOn(Schedulers.fromExecutor(PocHelper.executor("smith-", 2)))
                .map(analyzedEmail -> new ReactionForEmail())
                .log("smith-")
                .doOnNext(reactionForEmail -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
