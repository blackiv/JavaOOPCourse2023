package ru.ipanteev.oop23.lambda_main;

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

        System.out.println("Введите количество элементов потока, которое необходимо вычислить: ");
        int streamLimit = scanner.nextInt();

        System.out.println("Вычисленные значения:");
        System.out.println(Arrays.toString(squareRoots.limit(streamLimit).toArray()));
        System.out.println();

        System.out.println("Числа Фибоначчи, такое же количество:");

        int[] FibonacciNumbers = Stream.iterate(new int[]{0, 0}, x -> {
                    if (x[1] == 0) {
                        x[1] = 1;
                    } else {
                        x[1] = x[1] + x[0];
                        x[0] = x[1] - x[0];
                    }
                    return x;
                })
                .limit(streamLimit)
                .mapToInt(x -> x[1])
                .toArray();

        System.out.println(Arrays.toString(FibonacciNumbers));
    }
}
