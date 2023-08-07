package ru.ipanteev.oop23.shapes.comparators;

import ru.ipanteev.oop23.shapes.Shape;

import java.util.Comparator;

public class ShapePerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        if (shape1.getPerimeter() == shape2.getPerimeter()) {
            return 0;
        }

        return Double.compare(shape1.getPerimeter(), shape2.getPerimeter());
    }
}