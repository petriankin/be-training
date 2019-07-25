package threads;

import java.util.logging.Logger;

public class BlockedState {

    private static Logger LOGGER = Logger.getLogger(BlockedState.class.getName());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DemoThreadBS());
        Thread t2 = new Thread(new DemoThreadBS());

        t1.start();
        t2.start();

        Thread.sleep(1000);

        LOGGER.info(String.valueOf(t2.getState()));
        System.exit(0);
    }
}

class DemoThreadBS implements Runnable {
    @Override
    public void run() {
        commonResource();
    }

    public static synchronized void commonResource() {
        while (true) {
            // Infinite loop to mimic heavy processing
            // 't1' won't leave this method
            // when 't2' try to enters this
        }
    }
}
