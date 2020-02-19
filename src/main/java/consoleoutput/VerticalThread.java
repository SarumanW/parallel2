package consoleoutput;

public class VerticalThread extends Thread {
    private Printer printer;

    VerticalThread(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            printer.printVertical();
        }
    }
}
