package ru.ipanteev.oop23.shapes.comparators;

import ru.ipanteev.oop23.shapes.Shape;

import java.util.Comparator;

public class ShapeDescComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        if (shape1.getArea() == shape2.getArea()) {
            return 0;
        }

        return Double.compare(shape1.getArea(), shape2.getArea());
    }
}
