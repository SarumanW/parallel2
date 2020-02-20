package matrixmultiply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TapeAlgorithm implements MultiplicationAlgorithm {

    @Override
    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix) {
        int rowsCount = firstMatrix.length;

        List<TapeMultiplyThread> threads = new ArrayList<>();

        for (int i = 0; i < rowsCount; i++) {
            int[] row = firstMatrix[i];

            TapeMultiplyThread thread = new TapeMultiplyThread(i, row, resultMatrix);
            threads.add(thread);
        }

        for (int i = 0; i < rowsCount; i++) {
            int[] column = getColumn(secondMatrix, i);

            for (TapeMultiplyThread thread : threads) {
                thread.setColumn(column);
                thread.setJ(i);

                thread.run();
            }

            for (TapeMultiplyThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int[] getColumn(int[][] matrix, int column) {
        return Arrays.stream(matrix).mapToInt(ints -> ints[column]).toArray();
    }
}
