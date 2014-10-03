package demo.mock;
/*
 *
 * This class has a thread inside it - to simulate cron
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import demo.queue.*;

public class CronSimulator implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CronSimulator.class);
    private final ScheduledExecutorService cronSimulator = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scannerHandle;

    private final IQueueService listener;

    public CronSimulator(IQueueService listener) {
        this.listener = listener;
    }


    public void start() {
        log.info("start");
        scannerHandle = cronSimulator.scheduleAtFixedRate(this, 0, 3, TimeUnit.SECONDS);
    }

    public void stop() {
        log.info("stop");
        scannerHandle.cancel(true);
    }

    @Override
    public void run() {
        listener.manuallySeekTimeouts();
    }


}
