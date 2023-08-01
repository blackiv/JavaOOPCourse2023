package ru.ipanteev.oop23.shapes;

public class Circle implements Shape {
    public double getRadius() {
        return radius;
    }

    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getWidth() {
        return 2 * radius;
    }

    @Override
    public double getHeight() {
        return 2 * radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o.getClass() == getClass()) {
            Circle circle = (Circle) o;
            return circle.radius == radius;
        }

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + Double.hashCode(radius);
    }

    @Override
    public String toString() {
        return String.format("Круг с радиусом %.2f", radius);
    }
}
