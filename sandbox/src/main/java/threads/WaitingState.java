package threads;

import java.util.logging.Logger;

public class WaitingState implements Runnable {

    private static Logger LOGGER = Logger.getLogger(BlockedState.class.getName());
    public static Thread t1;

    /**'
     * We’ve created and started the t1
     * t1 creates a t2 and starts it
     * While the processing of t2 continues, we call t2.join(), this puts t1 in WAITING state until t2 has finished execution
     * Since t1 is waiting for t2 to complete, we’re calling t1.getState() from t2
     * @param args
     */

    public static void main(String[] args) {
        t1 = new Thread(new WaitingState());
        t1.start();
    }

    public void run() {
        Thread t2 = new Thread(new DemoThreadWS());
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.info("Thread interrupted");
        }
    }
}

class DemoThreadWS implements Runnable {
    private static Logger LOGGER = Logger.getLogger(DemoThreadWS.class.getName());
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.info("Thread interrupted");
        }
        LOGGER.info(String.valueOf(WaitingState.t1.getState()));
    }
}