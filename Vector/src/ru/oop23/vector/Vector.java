package ru.oop23.vector;

import java.util.Arrays;

public class Vector {
    private double[] vectorArray;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n");
        }

        vectorArray = new double[n];
    }

    public Vector(int n, double[] sourceVectorArray) {
        this(n);

        for (int i = 0; i < sourceVectorArray.length && i < n; i++) {
            this.vectorArray[i] = sourceVectorArray[i];
        }
    }

    public Vector(double[] sourceVectorArray) {
        this(sourceVectorArray.length, sourceVectorArray);
    }

    public Vector(Vector sourceVector) {
        this(sourceVector.vectorArray);
    }

    public int getSize() {
        return vectorArray.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        int size = getSize();

        for (int i = 0; i < size; i++) {
            stringBuilder.append(vectorArray[i]);
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    private void checkLengthResizeArray(int newSize) {
        if (newSize > getSize()) {
            vectorArray = Arrays.copyOf(vectorArray, newSize);
        }
    }

    public void addVector(Vector vector) {
        checkLengthResizeArray(vector.getSize());

        for (int i = 0; i < vector.getSize(); i++) {
            vectorArray[i] += vector.vectorArray[i];
        }
    }

    public void subtractVector(Vector vector) {
        checkLengthResizeArray(vector.getSize());

        for (int i = 0; i < vector.getSize(); i++) {
            vectorArray[i] -= vector.vectorArray[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < getSize(); i++) {
            vectorArray[i] *= scalar;
        }
    }

    public void reverse() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double coordinate2PowSum = 0;

        for (double coordinate : vectorArray) {
            coordinate2PowSum += coordinate * coordinate;
        }

        return Math.sqrt(coordinate2PowSum);
    }

    public double getCoordinateByIndex(int index) {
        if (index >= getSize() || index <= 0) {
            throw new IndexOutOfBoundsException("index");
        }

        return vectorArray[index];
    }

    public void setCoordinateByIndex(int index, double value) {
        if (index >= getSize() || index <= 0) {
            throw new IndexOutOfBoundsException("index");
        }

        vectorArray[index] = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        if (vector.getSize() != getSize())
            return false;

        for (int i = 0; i < getSize(); i++) {
            if (vectorArray[i] != vector.vectorArray[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vectorArray);
    }
}
