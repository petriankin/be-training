package threads;

import java.util.logging.Logger;

public class TerminatedState implements Runnable {
    private static Logger LOGGER = Logger.getLogger(TerminatedState.class.getName());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new TerminatedState());
        Thread.currentThread().getPriority()
        t1.start();

        // The following sleep method will give enough time for
        // thread t1 to complete
        Thread.sleep(1000);
        LOGGER.info(String.valueOf(t1.getState()));

    }
    @Override
    public void run() {
        // do nothing
    }
}
