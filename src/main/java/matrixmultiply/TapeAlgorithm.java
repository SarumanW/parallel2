package matrixmultiply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TapeAlgorithm implements MultiplicationAlgorithm {
    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);

    private List<Callable<Object>> tasks = new ArrayList<>();

    private List<Thread> threads = new ArrayList<>();


//    @Override
//    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix) {
//        int rowsCount = firstMatrix.length;
//
//        for (int i = 0; i < rowsCount; i++) {
//            int columnIndex = i;
//            for (int j = 0; j < rowsCount; j++) {
//                int element = multiplyRowColumn(firstMatrix[j], getColumn(secondMatrix, columnIndex));
//                Test.result[j][columnIndex] = element;
//                if (++columnIndex == rowsCount) {
//                    columnIndex = 0;
//                }
//            }
//        }
//    }


    @Override
    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix) {
        int rowsCount = firstMatrix.length;

        for (int i = 0; i < rowsCount; i++) {
            int columnIndex = i;
            for (int j = 0; j < rowsCount; j++) {

                int[] column = getColumn(secondMatrix, columnIndex);

                MultiplyThread multiplyThread = new MultiplyThread(j, columnIndex, firstMatrix[j], column);
                exec.execute(multiplyThread);

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

    public int multiplyRowColumn(int[] row, int[] column) {
        int result = 0;

        for (int i = 0; i < row.length; i++) {
            result += row[i] * column[i];
        }

        return result;
    }
}
