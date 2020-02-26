package producerconsumer;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int[] numbers;

    Producer(Drop drop, int arraySize) {
        this.drop = drop;
        this.numbers = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            this.numbers[i] = i;
        }
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int number : numbers) {
            drop.put(number);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ignored) {
            }
        }
        drop.put(numbers.length);
    }
}
