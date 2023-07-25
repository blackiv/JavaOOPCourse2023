package ru.oop23.shapes;

import java.util.Objects;

public class Square implements Shape {
    private final double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public double getWidth() {
        return sideLength;
    }

    @Override
    public double getHeight() {
        return sideLength;
    }

    @Override
    public double getArea() {
        return sideLength * sideLength;
    }

    @Override
    public double getPerimeter() {
        return 4 * sideLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square square)) return false;

        return square.sideLength == sideLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sideLength);
    }

    @Override
    public String toString() {
        return String.format("Квадрат со стороной %.2f", sideLength);
    }
}
