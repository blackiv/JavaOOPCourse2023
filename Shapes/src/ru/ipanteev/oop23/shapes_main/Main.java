package ru.ipanteev.oop23.shapes_main;

import ru.ipanteev.oop23.shapes.*;
import ru.ipanteev.oop23.shapes.comparators.ShapeDescComparator;
import ru.ipanteev.oop23.shapes.comparators.ShapePerimeterComparator;

import java.util.Arrays;

public class Main {
    private static Shape findMaxAreaShape(Shape[] shapes) {
        Arrays.sort(shapes, new ShapeDescComparator());
        return shapes[shapes.length - 1];
    }

    private static Shape findSecondMaxPerimeterShape(Shape[] shapes) {
        Arrays.sort(shapes, new ShapePerimeterComparator());
        return shapes[shapes.length - 2];
    }

    public static void main(String[] args) {
        Shape[] shapes = new Shape[]{
                new Triangle(1, 2, 3, 4, 5, 3),
                new Triangle(0, 0, 100, 100, 20, 0),
                new Square(4),
                new Square(8),
                new Rectangle(4, 5),
                new Rectangle(1, 15),
                new Circle(4),
                new Circle(7)
        };

        Shape shape = findMaxAreaShape(shapes);

        System.out.printf("Наибольшая площадь (%.2f) у фигуры %s", shape.getArea(), shape);
        System.out.println();

        shape = findSecondMaxPerimeterShape(shapes);

        System.out.printf("Вторая по периметру (%.2f) фигура %s", shape.getPerimeter(), shape);
    }
}
