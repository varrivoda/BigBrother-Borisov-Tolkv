package varrivoda.bigbrother.poc;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PocHelper {

    public static final AtomicInteger COUNTER = new AtomicInteger();

    public static Executor executor(String prefix, int threads) {
        return Executors.newFixedThreadPool(threads, new CustomizableThreadFactory(prefix));
    }
}
