package ru.oop23.shapes.comparators;

import ru.oop23.shapes.Shape;

import java.util.Comparator;

public class ShapePerimeterDescComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape o1, Shape o2) {
        if (o1.equals(o2)) {
            return 0;
        }

        // по убыванию
        if (o2.getPerimeter() > o1.getPerimeter()) {
            return 1;
        }

        return -1;
    }
}
