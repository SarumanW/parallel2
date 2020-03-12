package queuesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class SystemTest {
    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);

    public void testSystem() throws InterruptedException {
        BlockingQueue<String> tasksQueue = new LinkedBlockingDeque<>(10);
        BlockingQueue<String> declinedQueue = new LinkedBlockingDeque<>();

        TaskProducer taskProducer = new TaskProducer(tasksQueue, declinedQueue);

        List<TaskConsumer> taskConsumers = new ArrayList<>();
        for (int i = 0; i < POOL_SIZE - 1; i++) {
            taskConsumers.add(new TaskConsumer(tasksQueue));
        }

        DeclineTester declineTester = new DeclineTester(taskConsumers, tasksQueue, declinedQueue);

        exec.submit(taskProducer);
        for (TaskConsumer taskConsumer : taskConsumers) {
            exec.submit(taskConsumer);
        }
        exec.submit(declineTester);

        Thread.sleep(100000);
        exec.shutdownNow();
    }
}
