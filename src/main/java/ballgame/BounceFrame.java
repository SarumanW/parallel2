package ballgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BounceFrame extends JFrame {
    private BallCanvas canvas;
    private static final int WIDTH = 450;
    private static final int HEIGHT = 350;

    BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("ballgame.Bounce program");

        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas, Thread.NORM_PRIORITY);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        JButton buttonCreateRed = new JButton("Red");
        JButton buttonCreateBlue = new JButton("Blue");
        JButton buttonExperiment = new JButton("Experiment");

        buttonCreateRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas, Color.RED, Thread.MAX_PRIORITY);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonCreateBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas, Color.BLUE, Thread.MIN_PRIORITY);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonExperiment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1000; i++) {
                    Ball b = new Ball(canvas, Color.BLUE, Thread.MIN_PRIORITY);
                    canvas.add(b);
                }
                Ball b = new Ball(canvas, Color.RED, Thread.MAX_PRIORITY);
                canvas.add(b);

                //4 завдання
                BallThread previousBallThread = new BallThread();
                long wait = 500;
                for (int j = 0; j < canvas.getBalls().size(); j++) {
                    BallThread thread;
                    if (j == 0) {
                        thread = new BallThread(canvas.getBalls().get(j));
                    } else {
                        thread = new BallThread(canvas.getBalls().get(j), previousBallThread, wait);
                        wait = wait + 500;
                    }

                    previousBallThread = thread;
                    thread.start();
                }


//                for (Ball ball : canvas.getBalls()) {
//                    BallThread thread = new BallThread(ball);
//                    thread.start();
//                }

            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonCreateRed);
        buttonPanel.add(buttonCreateBlue);
        buttonPanel.add(buttonExperiment);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
