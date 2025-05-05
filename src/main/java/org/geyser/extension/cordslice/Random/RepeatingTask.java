package org.geyser.extension.cordslice.Random;

import java.util.concurrent.*;

public class RepeatingTask {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;

    public void start(long intervalMillis, Runnable task) {
        future = executor.scheduleWithFixedDelay(task, 0, intervalMillis, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (future != null) future.cancel(false);
        executor.shutdownNow();
    }
}

