package ru.ipanteev.oop23.shapes_main;

import ru.ipanteev.oop23.shapes.*;
import ru.ipanteev.oop23.shapes.comparators.ShapeAreaComparator;
import ru.ipanteev.oop23.shapes.comparators.ShapePerimeterComparator;

import java.util.Arrays;

public class Main {
    private static Shape getMaxAreaShape(Shape[] shapes) {
        if (shapes.length == 0) {
            return null;
        }

        Arrays.sort(shapes, new ShapeAreaComparator());
        return shapes[shapes.length - 1];
    }

    private static Shape getSecondMaxPerimeterShape(Shape[] shapes) {
        if (shapes.length < 2) {
            return null;
        }

        Arrays.sort(shapes, new ShapePerimeterComparator());
        return shapes[shapes.length - 2];
    }

    public static void main(String[] args) {
        Shape[] shapes = {
                new Triangle(1, 2, 3, 4, 5, 3),
                new Triangle(0, 0, 100, 100, 20, 0),
                new Square(4),
                new Square(8),
                new Rectangle(4, 5),
                new Rectangle(1, 15),
                new Circle(4),
                new Circle(7)
        };

        Shape maxAreaShape = getMaxAreaShape(shapes);

        if (maxAreaShape != null) {
            System.out.printf("Наибольшая площадь (%.2f) у фигуры %s%n", maxAreaShape.getArea(), maxAreaShape);
        }

        Shape secondMaxPerimeterShape = getSecondMaxPerimeterShape(shapes);

        if (secondMaxPerimeterShape != null) {
            System.out.printf("Вторая по периметру (%.2f) фигура %s", secondMaxPerimeterShape.getPerimeter(), secondMaxPerimeterShape);
        }
    }
}
