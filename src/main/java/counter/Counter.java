package counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int counter;
    private final static Object lock = new Object();
    private Lock lock1 = new ReentrantLock();

     void increment() {
//        synchronized (this) {
//            counter--;
//        }

        lock1.lock();
        counter++;
        lock1.unlock();


//        synchronized (lock) {
//            counter++;
//        }

        //this.counter++;

    }

     void decrement() {
//        synchronized (this) {
//            counter--;
//        }

//        synchronized (lock) {
//            counter--;
//        }

        lock1.lock();
        counter--;
        lock1.unlock();

        //this.counter--;
    }

    int getCounter() {
        return this.counter;
    }
}
