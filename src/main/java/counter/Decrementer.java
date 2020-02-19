package counter;

public class Decrementer extends Thread {
    private Counter counter;

    Decrementer(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.decrement();
        }
    }
}
