package cz.muni.fi.pv168.threads;

import java.util.concurrent.TimeUnit;

/*
  HINTS:

  How to set thread name?
      new Thread(runnable, "Thread 1")

  How to obtain current thread name?
      Thread.currentThread().getName()
 */
public class Counter {

    public static void main(String[] args) {
        // TODO: Start 3 threads, generating unique numbers from 0 to 50 and printing them to stdout
        // TODO: Spend 10 ms in waitActively() in between generating a number and printing it
    }

    /**
     * Waits for a given amount of time, without suspending the current thread.
     *
     * <p><b>WARNING:</b> Never use this busy-wait approach in real application!
     * The correct way is to use {@link Thread#sleep(long)}, which guarantees
     * that CPU resources are not wasted.</p>
     *
     * <p>However, we cant't use the correct way here, because it can mask
     * possible errors in synchronization. Immediate context switch might have
     * the same effect like {@link Thread#yield()}, which shouldn't be used
     * to "fix" synchronization problems (see Item 84 in Effective Java).</p>
     *
     * @param duration amount of time to spend actively waiting
     * @param timeUnit units in which {@code duration} is specified
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private static void waitActively(long duration, TimeUnit timeUnit) {
        long durationNanos = timeUnit.toNanos(duration);
        long start = System.nanoTime();
        while ((System.nanoTime() - start) < durationNanos);
    }
}
