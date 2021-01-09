package addressdatabase.time;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public class StopWatch {

    private static final double NANOSECONDS_IN_MILLISECOND = (double) TimeUnit.MILLISECONDS.toNanos(1);
    private final long startNanoTime;
    private final LongSupplier currentNanoTimeSupplier;

    private StopWatch(LongSupplier currentNanoTimeSupplier) {
        this.currentNanoTimeSupplier = currentNanoTimeSupplier;
        this.startNanoTime = currentNanoTimeSupplier.getAsLong();
    }

    public static StopWatch start() {
        return new StopWatch(System::nanoTime);
    }

    public double getDurationInMilliseconds() {
        long nowNanoTime = currentNanoTimeSupplier.getAsLong();
        long durationInNanos = nowNanoTime - startNanoTime;
        return durationInNanos / NANOSECONDS_IN_MILLISECOND;
    }
}
