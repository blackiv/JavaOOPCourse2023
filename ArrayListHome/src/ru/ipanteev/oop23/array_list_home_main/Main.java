package ru.ipanteev.oop23.array_list_home_main;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<String> loadLinesFromFileToList(String fileName) throws IOException {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader reader = new BufferedReader(fileReader)) {
            ArrayList<String> arrayList = new ArrayList<>();

            while (reader.ready()) {
                arrayList.add(reader.readLine());
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

    public static void main(String[] args) throws IOException {
        ArrayList<String> linesList = loadLinesFromFileToList("intNumbers.txt");
        System.out.println("ArrayList прочитан из файла");
        System.out.printf("Получен список %s%n", linesList);
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
