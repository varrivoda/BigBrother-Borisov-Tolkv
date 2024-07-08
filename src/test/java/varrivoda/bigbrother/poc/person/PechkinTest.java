package varrivoda.bigbrother.poc.person;

import org.junit.jupiter.api.Test;
import varrivoda.bigbrother.poc.PocHelper;

import static org.junit.jupiter.api.Assertions.*;

class PechkinTest {

    @Test
    void show_me_magic() {
        BigBrother bigBrother = new BigBrother();
        Pechkin    pechkin = new Pechkin();
        Smith      smith = new Smith();

        pechkin.letters()
                //.doOnSubscribe(subscription -> subscription.request(Long.MAX_VALUE))
                .transform(bigBrother::analyze)
                .transform(smith::reactFor)
                .doOnNext(reactionForEmail -> System.out.println("= " + PocHelper.COUNTER.decrementAndGet()))
                .blockLast();
    }
}