package matrixmultiply.fox.forkjoin;

import matrixmultiply.MultiplicationAlgorithm;

import java.util.concurrent.ForkJoinPool;

public class FoxForkJoinAlgorithm implements MultiplicationAlgorithm {
    @Override
    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        pool.submit(new FoxForkJoinTask(firstMatrix, secondMatrix, resultMatrix,
                0, 0, 0, 0, 0, 0, firstMatrix.length)).join();
    }
}
