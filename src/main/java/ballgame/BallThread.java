package ballgame;

public class BallThread extends Thread {
    private Ball b;
    private BallThread previousBallThread;
    private long wait;

    BallThread(Ball ball) {
        b = ball;
        this.setPriority(ball.getThreadPriority());
    }

    BallThread(Ball ball, BallThread ballThread, long wait) {
        this(ball);
        this.previousBallThread = ballThread;
        this.wait = wait;
    }

    BallThread() {
    }

    @Override
    public void run() {
        try {
            if (this.previousBallThread != null) {
                this.previousBallThread.join(wait);
            }

            for (int i = 1; i < 10000; i++) {
                b.move();

                //System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }

    }
}
