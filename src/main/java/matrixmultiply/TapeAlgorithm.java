package matrixmultiply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TapeAlgorithm implements MultiplicationAlgorithm {
    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);

    @Override
    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix) {
        int rowsCount = firstMatrix.length;
        List<FutureTask> tasks = new ArrayList<>();

        for (int i = 0; i < rowsCount; i++) {
            int columnIndex = i;
            for (int j = 0; j < rowsCount; j++) {

                int[] column = getColumn(secondMatrix, columnIndex);

                TapeMultiplyThread tapeMultiplyThread = new TapeMultiplyThread(j, columnIndex,
                        firstMatrix[j], column, resultMatrix);
                exec.execute(tapeMultiplyThread);

                if (++columnIndex == rowsCount) {
                    columnIndex = 0;
                }
            }
        }

        exec.shutdown();
    }

    private int[] getColumn(int[][] matrix, int column) {
        return Arrays.stream(matrix).mapToInt(ints -> ints[column]).toArray();
    }
}
