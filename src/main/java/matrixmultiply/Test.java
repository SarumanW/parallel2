package matrixmultiply;

import matrixmultiply.fox.executorservice.FoxAlgorithm;
import matrixmultiply.fox.forkjoin.FoxForkJoinAlgorithm;
import matrixmultiply.tape.TapeAlgorithm;

import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;

public class Test {
    private static final int MATRIX_SIZE = 5000;

    public static void main(String[] args) {
        MultiplicationAlgorithm tapeAlgorithm = new TapeAlgorithm();
        MultiplicationAlgorithm foxAlgorithm = new FoxAlgorithm();
        MultiplicationAlgorithm foxForkJoinAlgorithm = new FoxForkJoinAlgorithm();

        //testAlgorithmExecution(tapeAlgorithm);
        //testAlgorithmExecution(foxAlgorithm);
        testAlgorithmExecution(foxForkJoinAlgorithm);
    }

    private static void testAlgorithmExecution(MultiplicationAlgorithm algorithm) {
        int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] first = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        int[][] second = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        long startTime = System.nanoTime();
        algorithm.multiplyMatrix(first, second, result);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }

}
