package matrixmultiply;

import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;
import static matrixmultiply.MultiplicationUtils.printMatrix;

public class Test {
    static int[][] result;

    public static void main(String[] args) {
        int n = 1000;
        int m = 1000;

        result = new int[n][m];
        int[][] first = generateRandomMatrix(n, m);
        int[][] second = generateRandomMatrix(n, m);
        MultiplicationAlgorithm algorithm = new TapeAlgorithm();

        long startTime = System.nanoTime();
        int[][] result = algorithm.multiplyMatrix(first, second);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);

        //printMatrix(result);
    }
}
