package matrixmultiply;

import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;

public class Test {
    private static final int MATRIX_SIZE = 2000;
    static int[][] result;

    public static void main(String[] args) {
        result = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] first = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        int[][] second = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        MultiplicationAlgorithm algorithm = new TapeAlgorithm();

        long startTime = System.nanoTime();
        algorithm.multiplyMatrix(first, second);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);

        //printMatrix(result);
    }
}
