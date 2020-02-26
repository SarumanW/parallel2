package horstmanlisting;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    private static final int NTEST = 10000;
    //private int[] accounts;
    private AtomicIntegerArray atomicIntegerArray;
    private long ntransacts;
    private Lock lock1 = new ReentrantLock();

    Bank(int n, int initialBalance) {
        //accounts = new int[n];
        atomicIntegerArray = new AtomicIntegerArray(n);

        int i;
        for (i = 0; i < n; i++) {
            //accounts[i] = initialBalance;
            atomicIntegerArray.set(i, initialBalance);
        }

        ntransacts = 0;
    }

     void transfer(int from, int to, int amount) { //synchronized
        //lock1.lock();

         atomicIntegerArray.getAndAdd(from, -amount);
         atomicIntegerArray.getAndAdd(to, amount);

       // accounts[from] -= amount;
       // accounts[to] += amount; // not atomic!

        //lock1.unlock();

        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
        }
    }

    private void test() {
        int sum = 0;
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            sum += atomicIntegerArray.get(i);
        }
        System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
    }

    int size() {
        return atomicIntegerArray.length();
    }
}
