package matrixmultiply;

public class MultiplyThread implements Runnable {
    private int i;
    private int j;
    private int[] row;
    private int[] column;

    MultiplyThread(int i, int j, int[] row, int[] column) {
        this.i = i;
        this.j = j;
        this.row = row;
        this.column = column;
    }

    @Override
    public void run() {
        int result = 0;

        for (int i = 0; i < row.length; i++) {
            result += row[i] * column[i];
        }

        //Test.result[i][j] = result;
    }
}
