package ru.ipanteev.oop23.matrix;

import ru.ipanteev.oop23.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;
    private int columnsCount;

    public Matrix(Vector[] rows) {
        checkNullArgument(rows, "rows");

        if (rows.length == 0) {
            throw new IllegalArgumentException("Количество элементов массива rows должно быть больше 0");
        }

        this.rows = new Vector[rows.length];
        columnsCount = rows[0].getSize();

        for (int i = 1; i < rows.length; i++) {
            columnsCount = Math.max(columnsCount, rows[i].getSize());
        }

        for (int i = 0; i < rows.length; i++) {
            this.rows[i] = new Vector(columnsCount);
            this.rows[i].add(rows[i]);
        }
    }

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException(String.format("Передано количество строк матрицы равное  %d, количество строк должно быть больше 0", rowsCount));
        }

        if (columnsCount <= 0) {
            throw new IllegalArgumentException(String.format("Передано количество столбцов матрицы равное  %d, количество столбцов должно быть больше 0", columnsCount));
        }

        rows = new Vector[rowsCount];
        this.columnsCount = columnsCount;

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(double[][] matrixData) {
        checkNullArgument(matrixData, "matrixData");

        if (matrixData.length == 0) {
            throw new IllegalArgumentException("Количество строк matrixData должно быть больше 0");
        }

        rows = new Vector[matrixData.length];
        columnsCount = matrixData[0].length;

        for (int i = 1; i < matrixData.length; i++) {
            columnsCount = Math.max(columnsCount, matrixData[i].length);
        }

        if (columnsCount == 0) {
            throw new IllegalArgumentException("Количество столбцов matrixData должно быть больше 0");
        }

        for (int i = 0; i < matrixData.length; i++) {
            rows[i] = new Vector(columnsCount, matrixData[i]);
        }
    }

    public Matrix(Matrix matrixSource) {
        this(matrixSource.rows);
    }

    public int[] getSize() {
        return new int[]{
                rows.length
                , columnsCount
        };
    }

    private void checkIndexOutOfBounds(int index, int bound) {
        if (index < 0 || index > bound) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, bound));
        }
    }

    public void setRowAt(int index, Vector row) {
        checkIndexOutOfBounds(index, rows.length - 1);

        checkNullArgument(row, "row");

        if (row.getSize() != columnsCount) {
            throw new IllegalArgumentException(String.format("Размер переданной строки %d. Размер строки должен быть %d", row.getSize(), columnsCount));
        }

        rows[index] = row;
    }

    public Vector getRowAt(int index) {
        checkIndexOutOfBounds(index, rows.length - 1);

        return rows[index];
    }


    public Vector getColumnAt(int index) {
        checkIndexOutOfBounds(index, columnsCount - 1);

        double[] column = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            column[i] = rows[i].getCoordinateByIndex(index);
        }

        return new Vector(column);
    }

    public void transpose() {
        Vector[] transposeMatrix = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; i++) {
            transposeMatrix[i] = getColumnAt(i);
        }

        rows = transposeMatrix;
        columnsCount = rows[0].getSize();
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        if (rows.length != columnsCount) {
            throw new ArithmeticException("Определитель считается только для квадратных матриц.");
        }

        Matrix matrix = new Matrix(this);
        Vector[] rows = matrix.rows;
        double epsilon = 1E-10;
        int matrixSize = matrix.rows.length;
        int resultMultiplier = 1;

        for (int i = 0; i < matrixSize; i++) {
            if (Math.abs(matrix.rows[i].getCoordinateByIndex(i)) <= epsilon) {
                boolean isChanged = false;

                for (int j = i + 1; j < matrixSize; j++) {
                    if (Math.abs(rows[j].getCoordinateByIndex(i)) > epsilon && Math.abs(rows[i].getCoordinateByIndex(j)) > epsilon) {
                        Vector temp = rows[i];
                        rows[i] = rows[j];
                        rows[j] = temp;

                        // согласно литературе "мы можем переставить строки местами, сменив знак конечного значения."
                        resultMultiplier *= -1;
                        isChanged = true;

                        break;
                    }
                }

                if (!isChanged) {
                    return 0;
                }
            }
        }

        for (int i = 0; i < matrixSize - 1; i++) {
            double denominator;

            if (i == 0) {
                denominator = 1.0;
            } else {
                denominator = rows[i - 1].getCoordinateByIndex(i - 1);
            }

            for (int j = i + 1; j < matrixSize; j++) {
                for (int k = i + 1; k < matrixSize; k++) {
                    // проверка denominator == 0 сделана ранее
                    rows[j].setCoordinateByIndex(k, (rows[j].getCoordinateByIndex(k) * rows[i].getCoordinateByIndex(i) -
                            rows[j].getCoordinateByIndex(i) * rows[i].getCoordinateByIndex(k)) / denominator);
                }
            }
        }

        return rows[matrixSize - 1].getCoordinateByIndex(matrixSize - 1) * resultMultiplier;
    }

    @Override
    public String toString() {
        if (rows.length == 0)
            return "{}";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        for (Vector row : rows) {
            stringBuilder.append(row);
            stringBuilder.append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.append('}').toString();
    }

    public void multiplyByVector(Vector vector) {
        checkNullArgument(vector, "vector");

        if (vector.getSize() != columnsCount) {
            throw new IllegalArgumentException(String.format("Размер вектора %d. Размер вектора должен быть %d", vector.getSize(), columnsCount));
        }

        for (int i = 0; i < rows.length; i++) {
            double coordinate = Vector.getScalarProduct(rows[i], vector);
            rows[i] = new Vector(new double[]{coordinate});
        }

        columnsCount = rows[0].getSize();
    }

    public void add(Matrix matrix) {
        checkNullArgument(matrix, "matrix");

        if (!Arrays.equals(getSize(), matrix.getSize())) {
            throw new IllegalArgumentException(String.format("Размер матрицы %s. Размер матрицы должен быть %s", Arrays.toString(matrix.getSize()), Arrays.toString(getSize())));
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.getRowAt(i));
        }
    }

    public void subtract(Matrix matrix) {
        checkNullArgument(matrix, "matrix");

        if (!Arrays.equals(getSize(), matrix.getSize())) {
            throw new IllegalArgumentException(String.format("Размер матрицы %s. Размер матрицы должен быть %s", Arrays.toString(matrix.getSize()), Arrays.toString(getSize())));
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.getRowAt(i));
        }
    }

    private static void checkNullArgument(Object argument, String argumentName) {
        if (argument == null) {
            throw new NullPointerException(String.format("Аргумент %s не может быть null", argumentName));
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkNullArgument(matrix1, "matrix1");
        checkNullArgument(matrix2, "matrix2");

        if (!Arrays.equals(matrix1.getSize(), matrix2.getSize())) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }

        Matrix result = new Matrix(matrix1);
        result.add(matrix2);

        return result;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkNullArgument(matrix1, "matrix1");
        checkNullArgument(matrix2, "matrix2");

        if (!Arrays.equals(matrix1.getSize(), matrix2.getSize())) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }

        Matrix result = new Matrix(matrix1);
        result.subtract(matrix2);

        return result;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        checkNullArgument(matrix1, "matrix1");
        checkNullArgument(matrix2, "matrix2");

        if (matrix1.columnsCount != matrix2.rows.length) {
            throw new IllegalArgumentException(String.format("Количество строк matrix2 равно %d, должно быть %d.", matrix2.rows.length, matrix1.columnsCount));
        }

        int rowsCount = matrix1.rows.length;
        int columnsCount = matrix2.columnsCount;

        double[][] result = new double[rowsCount][columnsCount];

        for (int i = 0; i < rowsCount; i++) {
            Vector row = matrix1.getRowAt(i);
            for (int j = 0; j < columnsCount; j++) {
                result[i][j] = Vector.getScalarProduct(row, matrix2.getColumnAt(j));
            }
        }

        return new Matrix(result);
    }
}
