package consoleoutput;

public class HorizontalThread extends Thread {
    private Printer printer;

    HorizontalThread(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            printer.printHorizontal();
        }
    }
}
