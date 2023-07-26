package ru.oop23.vector_main;

import ru.oop23.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector = new Vector(new double[]{1.0, 2.0, 3.0});

        System.out.printf("Имеется вектор %s с длиной %.2f", vector, vector.getLength());
        System.out.println();

        System.out.println("Добавление пустого вектора большей размерности");
        vector.addVector(new Vector(5));
        System.out.println(vector);

        System.out.println("Разворот вектора");
        vector.reverse();
        System.out.println(vector);

        int index = 2;
        System.out.printf("Значение компоненты %d = %.2f", index + 1, vector.getCoordinateByIndex(index));
        System.out.println();

        double newValueBuIndex = 4.2;
        System.out.printf("Изменяем её значение на %.2f", newValueBuIndex);
        System.out.println();
        vector.setCoordinateByIndex(index, newValueBuIndex);
        System.out.println(vector);

        System.out.println("Скопируем вектор и вычтем его и текущего");
        Vector copiedVector = new Vector(vector);

        if (copiedVector.equals(vector)) {
            vector.subtractVector(copiedVector);
            System.out.println(vector);
        }
    }
}
