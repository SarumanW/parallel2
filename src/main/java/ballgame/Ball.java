package ballgame;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Ball {
    private BallCanvas canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;

    private Color color;
    private int threadPriority;

    Ball(BallCanvas c, Color color, int threadPriority) {
        this.canvas = c;
        x = 0;
        y = 0;

        this.color = color;
        this.threadPriority = threadPriority;
    }

    int getThreadPriority() {
        return threadPriority;
    }

    Ball(BallCanvas c, int threadPriority) {
        this.canvas = c;
        this.threadPriority = threadPriority;
        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    void draw(Graphics2D g2) {
        g2.setColor(color == null ? Color.darkGray : color);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    void move() {
        x += dx;
        y += dy;

        if (x < 0) {
            x = 0;
            dx = -dx;
        }

        if (y < 0) {
            y = 0;
            dy = -dy;
        }

        if (check(x, y)) {
            System.out.println("In loose");
            System.out.println("x = " + x);
            System.out.println("y = " + y);

            Thread.currentThread().interrupt();
            this.canvas.incrementBallsCount();
            this.canvas.deleteBall(this);
        }

        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }

        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }

        this.canvas.repaint();
    }

    private boolean check(int x, int y) {
        return (x + XSIZE >= this.canvas.getWidth() && y + YSIZE >= this.canvas.getHeight())
                || (x + XSIZE >= this.canvas.getWidth() && y == 0)
                || (x == 0 && y + YSIZE >= this.canvas.getHeight())
                || (x == 0 && y == 0);
    }
}
