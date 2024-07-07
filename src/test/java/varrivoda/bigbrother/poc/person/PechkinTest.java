package varrivoda.bigbrother.poc.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PechkinTest {

    @Test
    void show_me_magic() {
        BigBrother bigBrother = new BigBrother();
        Pechkin    pechkin = new Pechkin();
        Smith      smith = new Smith();

        pechkin.letters()
                .transform(bigBrother::analyze)
                .transform(smith::reactFor)
                .blockLast();
    }
}