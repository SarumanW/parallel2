package counter;

public class Test {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Incrementer incrementer = new Incrementer(counter);
        Decrementer decrementer = new Decrementer(counter);

        incrementer.start();
        decrementer.start();

        try {
            incrementer.join();
            decrementer.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        System.out.println(counter.getCounter());
    }
}
