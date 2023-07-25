package ru.oop23.shapes_main;

import ru.oop23.shapes.*;
import ru.oop23.shapes.comparators.ShapeAreaDescComparator;
import ru.oop23.shapes.comparators.ShapePerimeterDescComparator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[8];
        shapes[0] = new Triangle(1, 2, 3, 4, 5, 3);
        shapes[1] = new Triangle(0, 0, 10, 10, 20, 0);
        shapes[2] = new Square(4);
        shapes[3] = new Square(8);
        shapes[4] = new Rectangle(4, 5);
        shapes[5] = new Rectangle(1, 15);
        shapes[6] = new Circle(4);
        shapes[7] = new Circle(7);

        Arrays.sort(shapes,new ShapeAreaDescComparator());

        System.out.printf("Наибольшая площадь (%.2f) у фигуры %s", shapes[0].getArea(), shapes[0]);
        System.out.println();

        Arrays.sort(shapes, new ShapePerimeterDescComparator());

        System.out.printf("Вторая по периметру (%.2f) фигура %s", shapes[1].getPerimeter(), shapes[1]);
    }
}
