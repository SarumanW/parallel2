package queuesystem;

import java.util.concurrent.BlockingQueue;

public class TaskProducer implements Runnable {
    private BlockingQueue<String> tasksQueue;
    private BlockingQueue<String> declinedTasksQueue;

    TaskProducer(BlockingQueue<String> blockingQueue, BlockingQueue<String> declinedTasksQueue) {
        this.tasksQueue = blockingQueue;
        this.declinedTasksQueue = declinedTasksQueue;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (true) {
                String task = "task" + i;

                System.out.println("Produce task " + task);
                if (!this.tasksQueue.offer(task)) {
                    System.out.println("Task " + task + " is out of queue");
                    declinedTasksQueue.offer(task);
                }
                i++;


                Thread.sleep(500); //make random

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
