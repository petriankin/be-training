package threads;

import java.util.logging.Logger;

public class NewState {

    private static Logger LOGGER = Logger.getLogger(NewState.class.getName());

    public static void main(String[] args) {

        Thread thread = new Thread(() -> System.out.println("run"));
        LOGGER.info(String.valueOf(thread.getState()));

    }
}
