package matrixmultiply;

import java.util.Arrays;

public class TapeAlgorithm implements MultiplicationAlgorithm {

    @Override
    public int[][] multiplyMatrix(int[][] firstMatrix, int[][] secondMatrix) {
        int rowsCount = firstMatrix.length;
        int[][] result = new int[rowsCount][rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            int columnIndex = i;
            for (int j = 0; j < rowsCount; j++) {
                int element = multiplyRowColumn(firstMatrix[j], getColumn(secondMatrix, columnIndex));
                result[j][columnIndex] = element;
                if (++columnIndex == rowsCount) {
                    columnIndex = 0;
                }
            }
        }

        return result;
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
