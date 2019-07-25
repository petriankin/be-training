package threads;

import java.util.logging.Logger;

public class TimedWaitingState {
    private static Logger LOGGER = Logger.getLogger(TimedWaitingState.class.getName());

    public static void main(String[] args) throws InterruptedException {
        DemoThread demoThread = new DemoThread();
        Thread t1 = new Thread(demoThread);
        t1.start();

        // The following sleep will give enough time for ThreadScheduler
        // to start processing of thread t1
        Thread.sleep(1000);
        LOGGER.info(String.valueOf(t1.getState()));
    }
}

class DemoThread implements Runnable {
    private static Logger LOGGER = Logger.getLogger(DemoThread.class.getName());

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.info("Thread interrupted");
        }

    }
}
