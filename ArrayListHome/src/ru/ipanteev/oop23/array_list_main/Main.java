package ru.ipanteev.oop23.array_list_main;

import ru.ipanteev.oop23.array_list.ArrayList;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> integerArrayList = ArrayList.loadFromIntNumbersFile("intNumbers.txt");
        System.out.println("ArrayList прочитан из файла");
        System.out.printf("Получен список %s%n", integerArrayList);
        System.out.println();

        System.out.printf("Только уникальные значения %s%n", integerArrayList.getUniqueList());
        System.out.println();

        ArrayList.clearEvenNumber(integerArrayList);
        System.out.println("Все четные значения убраны из списка");
        System.out.printf("Получен список %s%n", integerArrayList);
    }
}
