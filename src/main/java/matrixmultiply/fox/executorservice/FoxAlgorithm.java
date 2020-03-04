package matrixmultiply.fox.executorservice;

import matrixmultiply.MultiplicationAlgorithm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FoxAlgorithm implements MultiplicationAlgorithm {

    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);

    @Override
    public void multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix, int[][] result) {
        Future f = exec.submit(new FoxMultiplyThread(firstMatrix, secondMatrix, result,
                0, 0, 0, 0, 0, 0, firstMatrix.length, this.exec));

        try {
            f.get();
            exec.shutdown();
        } catch (Exception ignored) {

        }
    }
}
