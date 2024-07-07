package varrivoda.bigbrother.poc.person;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import varrivoda.bigbrother.poc.model.AnalyzedEmail;
import varrivoda.bigbrother.poc.model.ReactionForEmail;

import java.util.concurrent.TimeUnit;

public class Smith {
    public Flux<ReactionForEmail> reactFor(Flux<AnalyzedEmail> analyzedEmailFlux) {
        return analyzedEmailFlux
                .log("smith-")
                .map(analyzedEmail -> new ReactionForEmail())
                .doOnNext(reactionForEmail -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
