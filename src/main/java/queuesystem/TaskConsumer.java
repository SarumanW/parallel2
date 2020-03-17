package queuesystem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TaskConsumer extends Thread {
    private BlockingQueue<String> tasksQueue;

    TaskConsumer(BlockingQueue<String> blockingQueue) {
        this.tasksQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String task = tasksQueue.take();
                System.out.println("Process task: " + task);
                Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5000 + 1));

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
