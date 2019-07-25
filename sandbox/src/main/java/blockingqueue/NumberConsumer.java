package blockingqueue;

import java.util.concurrent.BlockingQueue;

public class NumberConsumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int poisonPill;

    NumberConsumer(BlockingQueue<Integer> queue, int poisonPill) {
        this.queue = queue;
        this.poisonPill = poisonPill;
    }

    public void run() {
        try {
            while (true) {
                Integer number = queue.take();
                if (number.equals(poisonPill)) {
                    return;
                }
                String result = number.toString();
                System.out.println(Thread.currentThread().getName() + " result: " + result);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}