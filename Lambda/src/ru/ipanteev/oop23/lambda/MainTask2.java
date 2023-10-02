package ru.ipanteev.oop23.lambda;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainTask2 {
    public static void main(String[] args) {
        DoubleStream squareRoots = IntStream.iterate(0, x -> x + 1)
                .mapToDouble(Math::sqrt);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество элементов потока, которое необходимо вычислить:");
        int elementsCount = scanner.nextInt();

        System.out.println("Вычисленные значения:");
        System.out.println(Arrays.toString(squareRoots.limit(elementsCount).toArray()));
        System.out.println();

        System.out.println("Числа Фибоначчи, такое же количество:");

        int[] fibonacciNumbers = Stream.iterate(new int[]{0, 1}, x -> {
                    x[1] += x[0];
                    x[0] = x[1] - x[0];
                    return x;
                })
                .mapToInt(x -> x[0])
                .limit(elementsCount)
                .toArray();

        System.out.println(Arrays.toString(fibonacciNumbers));
    }
}
