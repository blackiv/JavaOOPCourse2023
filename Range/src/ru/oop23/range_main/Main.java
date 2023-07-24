package ru.oop23.range_main;

import ru.oop23.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(1, 5);

        System.out.printf("Длина интервала %s равна %.2f%n", range, range.getLength());

        double point = 3.0;

        if (range.isInside(point)) {
            System.out.printf("Точка %.2f находится внутри интервала%n", point);
        } else {
            System.out.printf("Точка %.2f находится внутри интервала%n", point);
        }

        System.out.println();

        Range range2 = new Range(3.0, 5.4);

        Range intersectionRange = range.getIntersectionRange(range2);
        if (intersectionRange == null) {
            System.out.printf("Интервалы %s и %s не пересекаются%n", range, range2);
        } else {
            System.out.printf("Пересечение интервалов %s и %s дает:%n", range, range2);
            System.out.println(intersectionRange);
        }

        System.out.println();

        range2.setFrom(10);
        range2.setTo(15);
        Range[] unionRanges = range.getUnionRanges(range2);

        System.out.printf("Объединение интервалов %s и %s дает:%n", range, range2);

        for (Range unionRange : unionRanges) {
            System.out.printf("%s%n", unionRange);
        }

        System.out.println();

        range2 = new Range(3, 8);
        Range[] differentRanges = range.getDifferentRanges(range2);

        System.out.printf("Разность интервалов %s и %s дает:%n", range, range2);

        for (Range differentRange : differentRanges) {
            System.out.printf("%s%n", differentRange);
        }
    }
}