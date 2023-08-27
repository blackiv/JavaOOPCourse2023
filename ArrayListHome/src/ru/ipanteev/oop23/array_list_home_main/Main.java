package ru.ipanteev.oop23.array_list_home_main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> loadArrayListFromFile(String fileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            ArrayList<String> arrayList = new ArrayList<>();

            while (scanner.hasNextInt()) {
                arrayList.add(scanner.nextLine());
            }

            return arrayList;
        }
    }

    public static void deleteEvenNumbers(ArrayList<Integer> integerArrayList) {
        int i = 0;

        while (i < integerArrayList.size()) {
            if (integerArrayList.get(i) % 2 == 0) {
                integerArrayList.remove(i);
                continue;
            }

            i++;
        }
    }

    public static ArrayList<Integer> getUniqueList(ArrayList<Integer> integerArrayList) {
        ArrayList<Integer> resultList = new ArrayList<>(integerArrayList.size());

        for (Integer item : integerArrayList) {
            if (!resultList.contains(item)) {
                resultList.add(item);
            }
        }

        return resultList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> stringArrayList = loadArrayListFromFile("intNumbers.txt");
        System.out.println("ArrayList прочитан из файла");
        System.out.printf("Получен список %s%n", stringArrayList);
        System.out.println();

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(5);
        integerArrayList.add(12);
        integerArrayList.add(4);
        integerArrayList.add(8);
        integerArrayList.add(1);
        integerArrayList.add(9);

        System.out.printf("Убираем все четные значения из списка %s%n", integerArrayList);
        deleteEvenNumbers(integerArrayList);
        System.out.printf("Получен список %s%n%n", integerArrayList);

        integerArrayList.add(5);
        integerArrayList.add(12);
        integerArrayList.add(4);
        integerArrayList.add(8);
        integerArrayList.add(1);
        integerArrayList.add(9);

        System.out.printf("Оставляем только уникальные значения из списка %s%n", integerArrayList);
        ArrayList<Integer> uniqueValuesList = getUniqueList(integerArrayList);
        System.out.printf("Получен список %s%n", uniqueValuesList);
    }
}
