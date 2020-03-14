package queuesystem;

public class Logger extends Thread {
    private SystemTest systemTest;

    Logger(SystemTest systemTest) {
        this.systemTest = systemTest;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println("Tasks queue size: " + systemTest.getTasksQueue().size());
                System.out.println("Declined tasks queue size: " + systemTest.getDeclinedQueue().size());
                System.out.println("Average queue size: " + (int) systemTest.getQueueSizeTester().getAverageQueueSize());

                int declinedTasksNumber = systemTest.getDeclinedQueue().size();
                int generalTasksNumber = systemTest.getTaskProducer().getGeneralTasksNumber();
                double declineProbability = (double) declinedTasksNumber / generalTasksNumber;

                System.out.println("Decline probability: " + String.format("%2f", declineProbability));
                System.out.println();

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
