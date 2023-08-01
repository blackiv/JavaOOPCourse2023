package ru.ipanteev.oop23.vector;

import java.util.Arrays;

public class Vector {
    private double[] coordinates;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(String.format("Передан размер вектора равный %d, размер вектора должен быть больше 0", size));
        }

        coordinates = new double[size];
    }

    public Vector(int size, double[] coordinates) {
        this(size);

        System.arraycopy(coordinates, 0, this.coordinates, 0,
                Math.min(coordinates.length, size));
    }

    public Vector(double[] coordinates) {
        this(coordinates.length, coordinates);
    }

    public Vector(Vector vector) {
        this(vector.coordinates);
    }

    public int getSize() {
        return coordinates.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double coordinate : coordinates) {
            stringBuilder.append(coordinate);
            stringBuilder.append(", ");
        }

        if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        stringBuilder.append('}');

        return stringBuilder.toString();
    }

    private void increaseSize(int size) {
        if (size > coordinates.length) {
            coordinates = Arrays.copyOf(coordinates, size);
        }
    }

    public void add(Vector vector) {
        increaseSize(vector.coordinates.length);

        for (int i = 0; i < vector.coordinates.length; i++) {
            coordinates[i] += vector.coordinates[i];
        }
    }

    public void subtract(Vector vector) {
        increaseSize(vector.coordinates.length);

        for (int i = 0; i < vector.coordinates.length; i++) {
            coordinates[i] -= vector.coordinates[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] *= scalar;
        }
    }

    public void reverse() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double sum = 0;

        for (double coordinate : coordinates) {
            sum += coordinate * coordinate;
        }

        return Math.sqrt(sum);
    }

    public double getCoordinateByIndex(int index) {
        checkIndexOutOfBounds(index);
        return coordinates[index];
    }

    public void setCoordinateByIndex(int index, double value) {
        checkIndexOutOfBounds(index);
        coordinates[index] = value;
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= coordinates.length) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Индекс должен быть в пределах от 0 до %d", coordinates.length));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o.getClass() == getClass()) {
            Vector vector = (Vector) o;

            return vector.coordinates.length == coordinates.length && Arrays.equals(vector.coordinates, coordinates);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    public static Vector getVectorsSum(Vector vector1, Vector vector2) {
        Vector result = new Vector(vector1);
        result.add(vector2);

        return result;
    }

    public static Vector getVectorsDifference(Vector vector1, Vector vector2) {
        Vector result = new Vector(vector1);
        result.subtract(vector2);

        return result;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        int minSize = Math.min(vector1.coordinates.length, vector2.coordinates.length);
        double result = 0;

        for (int i = 0; i < minSize; i++) {
            result += vector1.getCoordinateByIndex(i) * vector2.getCoordinateByIndex(i);
        }

        return result;
    }
}
