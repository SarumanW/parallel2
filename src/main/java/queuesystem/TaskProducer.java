package queuesystem;

import java.util.concurrent.BlockingQueue;

public class TaskProducer extends Thread {
    private BlockingQueue<String> tasksQueue;
    private BlockingQueue<String> declinedTasksQueue;
    private int generalTasksNumber = 0;

    TaskProducer(BlockingQueue<String> blockingQueue, BlockingQueue<String> declinedTasksQueue) {
        this.tasksQueue = blockingQueue;
        this.declinedTasksQueue = declinedTasksQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String task = "task" + ++generalTasksNumber;

                if (!this.tasksQueue.offer(task)) {
                    declinedTasksQueue.offer(task);
                }

                System.out.println("Put task: " + task);
                Thread.sleep(600);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    int getGeneralTasksNumber() {
        return generalTasksNumber;
    }
}
