package matrixmultiply.fox.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class FoxForkJoinTask extends RecursiveAction {

    private int[][] a;
    private int[][] b;
    private int[][] c;
    private int a_i, a_j, b_i, b_j, c_i, c_j, size;

    private static final int MINIMUM_THRESHOLD = 64;

    FoxForkJoinTask(int[][] a, int[][] b, int[][] c, int a_i, int a_j, int b_i, int b_j, int c_i, int c_j, int size) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.a_i = a_i;
        this.a_j = a_j;
        this.b_i = b_i;
        this.b_j = b_j;
        this.c_i = c_i;
        this.c_j = c_j;
        this.size = size;
    }

    public void compute() {
        int h = size / 2;

        if (size <= MINIMUM_THRESHOLD) {
            int[][] matrix = this.multiplyMatrices(copyPartOfArray(a, a_i, a_j, size), copyPartOfArray(b, b_i, b_j, size));

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    c[c_i + i][c_j + j] += matrix[i][j];
                }
            }
        } else {
            ForkJoinTask.invokeAll(createSubtasks(h));
        }
    }

    private List<FoxForkJoinTask> createSubtasks(int h) {
        List<FoxForkJoinTask> subtasks = new ArrayList<>();

        subtasks.add(new FoxForkJoinTask(a, b, c, a_i, a_j, b_i, b_j, c_i, c_j, h));
        subtasks.add(new FoxForkJoinTask(a, b, c, a_i, a_j, b_i, b_j + h, c_i, c_j + h, h));
        subtasks.add(new FoxForkJoinTask(a, b, c, a_i + h, a_j + h, b_i + h, b_j, c_i + h, c_j, h));
        subtasks.add(new FoxForkJoinTask(a, b, c, a_i + h, a_j + h, b_i + h, b_j + h, c_i + h, c_j + h, h));

        subtasks.add(new FoxForkJoinTask(a, b, c, a_i, a_j + h, b_i + h, b_j, c_i, c_j, h));
        subtasks.add(new FoxForkJoinTask(a, b, c, a_i, a_j + h, b_i + h, b_j + h, c_i, c_j + h, h));
        subtasks.add(new FoxForkJoinTask(a, b, c, a_i + h, a_j, b_i, b_j, c_i + h, c_j, h));
        subtasks.add(new FoxForkJoinTask(a, b, c, a_i + h, a_j, b_i, b_j + h, c_i + h, c_j + h, h));

        return subtasks;
    }

    private int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix) {
        int[][] result = new int[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    private int multiplyMatricesCell(int[][] firstMatrix, int[][] secondMatrix, int row, int col) {
        int cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    private int[][] copyPartOfArray(int[][] oldArray, int i, int j, int size) {
        int[][] newArr = new int[size][size];

        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                newArr[k][l] = oldArray[k + i][l + j];
            }
        }

        return newArr;
    }
}
