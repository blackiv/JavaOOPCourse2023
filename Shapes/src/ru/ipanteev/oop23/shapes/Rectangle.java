package ru.ipanteev.oop23.shapes;

public class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o.getClass() == getClass()) {
            Rectangle rectangle = (Rectangle) o;
            return rectangle.width == width && rectangle.height == height;
        }

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(height);
        hash = prime * hash + Double.hashCode(width);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Прямоугольник шириной %.2f и высотой %.2f", width, height);
    }
}
