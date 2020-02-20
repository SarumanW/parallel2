package matrixmultiply;

public class TapeMultiplyThread implements Runnable {
    private int i;
    private int j;
    private int[] row;
    private int[] column;
    private int[][] resultMatrix;

    TapeMultiplyThread(int i, int j, int[] row, int[] column, int[][] resultMatrix) {
        this.i = i;
        this.j = j;
        this.row = row;
        this.column = column;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void run() {
        int result = 0;

        for (int i = 0; i < row.length; i++) {
            result += row[i] * column[i];
        }

        resultMatrix[i][j] = result;
    }
}
