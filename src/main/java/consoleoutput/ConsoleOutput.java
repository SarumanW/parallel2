package consoleoutput;

public class ConsoleOutput {
    public static void main(String[] args) {
        Printer printer = new Printer();
        HorizontalThread horizontalThread = new HorizontalThread(printer);
        VerticalThread verticalThread = new VerticalThread(printer);

        horizontalThread.start();
        verticalThread.start();
    }
}
