package producerconsumer;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;
    private int maxSize;

    Consumer(Drop drop, int maxSize) {
        this.drop = drop;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int number = drop.take();
             number != ++maxSize;
             number = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", number);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
