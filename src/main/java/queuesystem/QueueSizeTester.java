package queuesystem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueSizeTester extends Thread {
    private BlockingQueue<String> tasksQueue;
    private BlockingQueue<Integer> queueSizes;

    QueueSizeTester(BlockingQueue<String> tasksQueue) {
        this.tasksQueue = tasksQueue;
        this.queueSizes = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                queueSizes.add(tasksQueue.size());

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    double getAverageQueueSize() {
        return queueSizes.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }
}
