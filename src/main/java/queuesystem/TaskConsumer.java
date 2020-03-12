package queuesystem;

import java.util.concurrent.BlockingQueue;

public class TaskConsumer implements Runnable {
    private BlockingQueue<String> tasksQueue;
    private boolean isBusy = false;

    TaskConsumer(BlockingQueue<String> blockingQueue) {
        this.tasksQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String task = tasksQueue.take();
                System.out.println(task + " is taken by " + Thread.currentThread().getName());
                isBusy = true;
                Thread.sleep(5000); //make random

                System.out.println(task + " is released by " + Thread.currentThread().getName());
                isBusy = false;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isBusy() {
        return isBusy;
    }
}
