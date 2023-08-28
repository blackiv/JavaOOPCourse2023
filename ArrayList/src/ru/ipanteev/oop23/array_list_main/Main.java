package ru.ipanteev.oop23.array_list_main;

import ru.ipanteev.oop23.array_list.ArrayList;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> integerArrayList = new ArrayList<>(new Integer[]{1, 2, 5, 7});
        integerArrayList.add(9);
        integerArrayList.add(2, 9);

        System.out.printf("Исходный ArrayList %s%n%n", integerArrayList);

        Collection<Integer> addCollection = List.of(2, 5, 6);
        System.out.printf("Добавление коллекции %s%n", addCollection);
        integerArrayList.addAll(addCollection);
        System.out.printf("ArrayList %s%n%n", integerArrayList);

        addCollection = List.of(15, 27, 8, 4);
        int index = 3;
        System.out.printf("Добавление коллекции %s по индексу %d %n", addCollection, index);
        integerArrayList.addAll(3, addCollection);
        System.out.printf("ArrayList %s%n%n", integerArrayList);

        index = 5;
        System.out.printf("Удаление значения по индексу %d%n", index);
        System.out.printf("Удалено значение %d%n", integerArrayList.remove(index));
        System.out.printf("ArrayList %s%n%n", integerArrayList);

        Collection<Integer> deleteCollection = List.of(27, 5, 7);
        System.out.printf("Удаление коллекции %s%n", deleteCollection);
        integerArrayList.removeAll(deleteCollection);
        System.out.printf("ArrayList %s%n%n", integerArrayList);

        deleteCollection = List.of(2, 6, 15, 9);
        System.out.printf("Удаление из списка всех значений которых нет в коллекции %s%n", deleteCollection);
        integerArrayList.retainAll(deleteCollection);
        System.out.printf("ArrayList %s%n%n", integerArrayList);

        int indexOfValue = 9;
        System.out.printf("Индекс последнего вхождения значения %d в коллекции %s равен %d%n%n", indexOfValue, integerArrayList, integerArrayList.lastIndexOf(indexOfValue));

        System.out.println("Печать списка через foreach");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (Integer item : integerArrayList) {
            stringBuilder.append(item);
            stringBuilder.append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');
        System.out.println(stringBuilder);

        System.out.println("Тест изменения коллекции при выполнении foreach");

        try {
            for (Integer ignored : integerArrayList) {
                integerArrayList.add(10);
            }

            System.out.println("Ошибки не было, этого сообщения быть не должно");
        } catch (ConcurrentModificationException e) {
            System.out.printf("Произошла ошибка \"%s\"", e.getMessage());
        }
    }
}
