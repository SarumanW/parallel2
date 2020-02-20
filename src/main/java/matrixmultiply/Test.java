package matrixmultiply;

import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;
import static matrixmultiply.MultiplicationUtils.printMatrix;

public class Test {
    private static final int MATRIX_SIZE = 1000;

    public static void main(String[] args) {
        int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] first = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        int[][] second = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
//
        MultiplicationAlgorithm algorithm = new TapeAlgorithm();

        long startTime = System.nanoTime();
        algorithm.multiplyMatrix(first, second, result);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);



//        MultiplicationAlgorithm algorithm = new FoxAlgorithm();
//
//        long startTime = System.nanoTime();
//        algorithm.multiplyMatrix(first, second, result);
//        long endTime = System.nanoTime();
//
//        long duration = (endTime - startTime) / 1000000;
//        System.out.println(duration);
    }

}
