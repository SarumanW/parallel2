package matrixmultiply;

public class MultiplyThread extends Thread {
    private int[] row;
    private int[] column;

    MultiplyThread(int[] row, int[] column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public void run() {
        int result = 0;

        for (int i = 0; i < row.length; i++) {
            result += row[i] * column[i];
        }

        Test.result[0][0] = result;
    }
}
