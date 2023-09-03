package ru.ipanteev.oop23.matrix_main;

import ru.ipanteev.oop23.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(new double[][]{
                {0.1, 2.6, 1.6},
                {2.0, 5.0, 9.0},
                {7.5, 4.0, 12.0}
        });

        System.out.printf("Имеется матрица %s%n", matrix);
        System.out.printf("Размер матрицы %sx%s%n", matrix.getRowsCount(), matrix.getColumnsCount());
        System.out.printf("Определитель = %.2f%n", matrix.getDeterminant());

        Matrix transponsedMatrix = new Matrix(matrix);
        transponsedMatrix.transpose();
        System.out.printf("Транспонированная матрица %s%n", transponsedMatrix);
        System.out.println();

        Matrix matrix2 = new Matrix(new double[][]{
                {4.3, 6.1, 3.6},
                {5.0, 5.2, 9.0},
                {7.0, 3.4, 6.0}
        });
        matrix.multiplyByScalar(5);

        System.out.printf("Сложение матриц %s и %s", matrix, matrix2);
        System.out.println();

        Matrix resultMatrix = Matrix.getSum(matrix, matrix2);
        System.out.printf("Результат %s", resultMatrix);
        System.out.println();
        System.out.println();

        System.out.printf("Произведение матриц %s и %s", matrix, matrix2);
        System.out.println();

        resultMatrix = Matrix.getProduct(matrix, matrix2);
        System.out.printf("Результат %s", resultMatrix);
        System.out.println();
    }
}
