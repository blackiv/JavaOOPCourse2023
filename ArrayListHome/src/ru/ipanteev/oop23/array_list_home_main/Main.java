package ru.ipanteev.oop23.array_list_home_main;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<String> loadLinesFromFileToList(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> resultList = new ArrayList<>();

            while (true) {
                String line = reader.readLine();

                if (line == null) {
                    break;
                }

                resultList.add(line);
            }

            return resultList;
        }
    }

    public static void deleteEvenNumbers(ArrayList<Integer> integersList) {
        int i = 0;

        while (i < integersList.size()) {
            if (integersList.get(i) % 2 == 0) {
                integersList.remove(i);
                continue;
            }

            i++;
        }
    }

    public static ArrayList<Integer> getUniqueList(ArrayList<Integer> integersList) {
        ArrayList<Integer> resultList = new ArrayList<>(integersList.size());

        for (Integer item : integersList) {
            if (!resultList.contains(item)) {
                resultList.add(item);
            }
        }

        return resultList;
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> linesList = loadLinesFromFileToList("intNumbers.txt");
            System.out.println("ArrayList прочитан из файла");
            System.out.printf("Получен список %s%n", linesList);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        System.out.println();

        ArrayList<Integer> integersList = new ArrayList<>();
        integersList.add(5);
        integersList.add(12);
        integersList.add(4);
        integersList.add(8);
        integersList.add(1);
        integersList.add(9);

        System.out.printf("Убираем все четные значения из списка %s%n", integersList);
        deleteEvenNumbers(integersList);
        System.out.printf("Получен список %s%n%n", integersList);

        integersList.add(5);
        integersList.add(12);
        integersList.add(4);
        integersList.add(8);
        integersList.add(1);
        integersList.add(9);

        System.out.printf("Оставляем только уникальные значения из списка %s%n", integersList);
        ArrayList<Integer> uniqueValuesList = getUniqueList(integersList);
        System.out.printf("Получен список %s%n", uniqueValuesList);
    }
}
