package ru.ipanteev.oop23.hash_table_main;

import ru.ipanteev.oop23.hash_table.HashTable;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashTable<String> hashTable = new HashTable<>();
        hashTable.addAll(List.of(
                "Москва",
                "Самара",
                "Уфа",
                "Владивосток",
                "Москва",
                "Уфа"));

        hashTable.add(null);

        System.out.printf("Создана хеш-таблица %s%n", hashTable);
        System.out.printf("Размер = %d%n%n", hashTable.size());

        String addedString = "Новосибирск";
        System.out.printf("Добавление одного элемента %s%n", addedString);
        hashTable.add(addedString);

        System.out.printf("проверим что элемент %s добавился%n", addedString);

        if (hashTable.contains(addedString)) {
            System.out.println("  таблица содержит это значение.");
        } else {
            System.out.println("  таблица не содержит это значение.");
        }

        System.out.println();

        System.out.println("Печать коллекции через foreach");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        if (!hashTable.isEmpty()) {
            for (String item : hashTable) {
                stringBuilder.append(item).append(", ");
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        stringBuilder.append(']');
        System.out.println(stringBuilder);
        System.out.println();

        /* не нашел как в List.of allowNull установить, а без этого коллекция ругается при проверке есть ли в ней null */
        ArrayList<String> deletedValuesCollection = new ArrayList<>(List.of("Самара", "Саратов"));
        System.out.printf("Удаление коллекции %s%n", deletedValuesCollection);
        hashTable.removeAll(deletedValuesCollection);
        System.out.printf("hashTable %s%n%n", hashTable);

        ArrayList<String> retainedValuesCollection = new ArrayList<>(List.of("Москва", "Новосибирск", "Владивосток"));
        System.out.printf("Удаление из списка всех значений которых нет в коллекции %s%n", retainedValuesCollection);
        hashTable.retainAll(retainedValuesCollection);
        System.out.printf("hashTable %s%n%n", hashTable);
    }
}
