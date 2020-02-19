package ballgame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BallCanvas extends JPanel {
    private List<Ball> balls = new ArrayList<>();
    private AtomicInteger ballsCount = new AtomicInteger(0);

    void add(Ball b) {
        this.balls.add(b);
    }

    void incrementBallsCount() {
        this.ballsCount.incrementAndGet();
    }

    void deleteBall(Ball ball) {
        this.balls.remove(ball);
    }

    List<Ball> getBalls() {
        return balls;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            b.draw(g2);
        }

        g.setColor(Color.BLACK);
        g.drawString("Balls in pots: " + ballsCount.get(), 50, 10);
    }
}
