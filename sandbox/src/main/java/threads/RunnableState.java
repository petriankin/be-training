package threads;

import java.util.logging.Logger;

public class RunnableState {

    private static Logger LOGGER = Logger.getLogger(RunnableState.class.getName());

    public static void main(String[] args) {

        Thread thread = new Thread(() -> System.out.println("run"));
        thread.start();
        LOGGER.info(String.valueOf(thread.getState()));

    }
}
