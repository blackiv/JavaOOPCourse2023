package ru.ipanteev.oop23.shapes;

public class Square implements Shape {
    private final double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
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
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Square square = (Square) o;
        return square.sideLength == sideLength;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + Double.hashCode(sideLength);
    }

    @Override
    public String toString() {
        return String.format("Квадрат со стороной %.2f", sideLength);
    }
}
