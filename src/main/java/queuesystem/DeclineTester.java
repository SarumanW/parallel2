package queuesystem;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DeclineTester implements Runnable {
    private List<TaskConsumer> taskConsumers;
    private BlockingQueue<String> tasksQueue;
    private BlockingQueue<String> declinedQueue;

    DeclineTester(List<TaskConsumer> taskConsumers,
                  BlockingQueue<String> tasksQueue,
                  BlockingQueue<String> declinedQueue) {
        this.taskConsumers = taskConsumers;
        this.tasksQueue = tasksQueue;
        this.declinedQueue = declinedQueue;
    }

    @Override
    public void run() {
        while (true) {
            boolean areAllConsumersBusy = taskConsumers.stream().allMatch(TaskConsumer::isBusy);
            if (areAllConsumersBusy && !tasksQueue.isEmpty()) {
                try {
                    String task = tasksQueue.take();
                    declinedQueue.offer(task);
                    System.out.println("Task " + task + "is declined!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
