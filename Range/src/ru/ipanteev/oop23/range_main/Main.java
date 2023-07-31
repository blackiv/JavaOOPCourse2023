package ru.ipanteev.oop23.range_main;

import ru.ipanteev.oop23.range.Range;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(2, 4);

        System.out.printf("Длина интервала (%.2f; %.2f) равна %.2f%n", range1.getFrom(), range1.getTo(), range1.getLength());

        range1.setFrom(1);
        range1.setTo(5);

        System.out.printf("Длина интервала %s равна %.2f%n", range1, range1.getLength());

        double point = 3.0;

        if (range1.isInside(point)) {
            System.out.printf("Точка %.2f находится внутри интервала%n", point);
        } else {
            System.out.printf("Точка %.2f находится внутри интервала%n", point);
        }

        System.out.println();

        Range range2 = new Range(3.0, 5.4);

        Range intersection = range1.getIntersection(range2);

        if (intersection == null) {
            System.out.printf("Интервалы %s и %s не пересекаются%n", range1, range2);
        } else {
            System.out.printf("Пересечение интервалов %s и %s дает:%n", range1, range2);
            System.out.println(intersection);
        }

        System.out.println();

        Range[] union = range1.getUnion(range2);

        System.out.printf("Объединение интервалов %s и %s дает:%n", range1, range2);
        System.out.println(Arrays.toString(union));
        System.out.println();

        Range[] difference = range1.getDifference(range2);

        System.out.printf("Разность интервалов %s и %s дает:%n", range1, range2);

        if (difference.length > 0) {
            System.out.println(Arrays.toString(difference));
        } else {
            System.out.println("Пусто");
        }
    }
}