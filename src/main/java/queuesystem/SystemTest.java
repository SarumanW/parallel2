package queuesystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Data
@AllArgsConstructor
@NoArgsConstructor
class SystemTest {
    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);

    private static final int CONSUMERS_SIZE = 3;

    private BlockingQueue<String> tasksQueue;
    private BlockingQueue<String> declinedQueue;
    private TaskProducer taskProducer;
    private QueueSizeTester queueSizeTester;

    void testSystem() throws InterruptedException {
        tasksQueue = new LinkedBlockingDeque<>(10);
        declinedQueue = new LinkedBlockingDeque<>();

        taskProducer = new TaskProducer(tasksQueue, declinedQueue);

        List<TaskConsumer> taskConsumers = new ArrayList<>();
        for (int i = 0; i < CONSUMERS_SIZE; i++) {
            taskConsumers.add(new TaskConsumer(tasksQueue));
        }

        queueSizeTester = new QueueSizeTester(tasksQueue);
        Logger logger = new Logger(this);

        exec.submit(taskProducer);
        exec.submit(queueSizeTester);
        for (TaskConsumer taskConsumer : taskConsumers) {
            exec.submit(taskConsumer);
        }
        exec.submit(queueSizeTester);
        //exec.submit(logger);

        Thread.sleep(100000);
        exec.shutdownNow();

        int declinedTasksNumber = declinedQueue.size();
        int generalTasksNumber = taskProducer.getGeneralTasksNumber();
        double declineProbability = (double) declinedTasksNumber / generalTasksNumber;

        System.out.println("Decline probability: " + declineProbability);
        System.out.println("Average queue size: " + queueSizeTester.getAverageQueueSize());
    }
}

