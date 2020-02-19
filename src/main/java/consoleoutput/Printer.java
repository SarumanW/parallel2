package consoleoutput;

class Printer {

    private int row;
    private int rowCount;
    private boolean isHorizontal;

    synchronized void printHorizontal() {
        while (isHorizontal) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (row != 100) {
            System.out.print("-");
            row++;
        } else {
            System.out.println("-");
            if (++rowCount == 100) {
                Thread.currentThread().interrupt();
                return;
            }
            row = 0;
        }
        isHorizontal = true;
        notify();
    }

    synchronized void printVertical() {
        while (!isHorizontal) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (row != 100) {
            System.out.print("|");
            row++;
        } else {
            System.out.println("|");
            if (++rowCount == 100) {
                Thread.currentThread().interrupt();
                return;
            }
            row = 0;
        }

        isHorizontal = false;
        notify();
    }
}
