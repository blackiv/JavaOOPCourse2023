package ru.ipanteev.oop23.list_main;

import ru.ipanteev.oop23.list.LinkedList;
import ru.ipanteev.oop23.list.ListItem;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        linkedList.insert(new ListItem<>(0), 0);
        linkedList.insert(new ListItem<>(1), 1);
        linkedList.insert(new ListItem<>(2), 2);
        linkedList.insert(new ListItem<>(3), 3);
        linkedList.insert(new ListItem<>(4), 4);
        linkedList.insert(new ListItem<>(5), 5);
        linkedList.insert(new ListItem<>(6), 6);

        System.out.printf("Список %s%n", linkedList);
        System.out.printf("Длина равна %d%n", linkedList.getSize());
        System.out.printf("Первое значение %d%n", linkedList.getFirstValue());

        int index = 3;
        System.out.printf("Значение по индексу %d = %d%n%n", index, linkedList.getValue(index));

        index = 2;
        int newValue = 9;
        System.out.printf("Изменяем значение по индексу %d на %d%n", index, newValue);
        System.out.printf("Было - %d, стало - %d%n%n", linkedList.setValue(newValue, index), linkedList.getValue(index));
        System.out.printf("Список %s%n%n", linkedList);

        newValue = 10;
        System.out.printf("Вставка нового элемента со значением %d в начало%n", newValue);
        linkedList.insertFirst(new ListItem<>(newValue));
        System.out.printf("Список %s%n%n", linkedList);

        newValue = 22;
        index = 4;
        System.out.printf("Вставка нового элемента со значением %d по индексу %d%n", newValue, index);
        linkedList.insert(new ListItem<>(newValue), index);
        System.out.printf("Список %s%n%n", linkedList);

        int deleteValue = 1;
        System.out.printf("Удаление из списка элемента со значением %d%n", deleteValue);
        if (linkedList.removeByValue(deleteValue)) {
            System.out.println("Элемент был найден и удален из списка");
        } else {
            System.out.println("Элемент не был найден в списке");
        }
        System.out.printf("Список %s%n", linkedList);
        System.out.printf("Длина равна %d%n%n", linkedList.getSize());

        index = 1;
        System.out.printf("Удаление из списка элемента индексу %d%n", index);
        System.out.printf("Удален элемент со значением %d%n",  linkedList.removeAt(index));
        System.out.printf("Список %s%n%n", linkedList);

        System.out.println("Удаление из списка первого элемента");
        System.out.printf("Удален элемент со значением %d%n",  linkedList.removeFirst());
        System.out.printf("Список %s%n%n", linkedList);


        System.out.printf("Разворот списка %s%n", linkedList);
        linkedList.reverse();
        System.out.printf("Список %s%n%n", linkedList);

        System.out.printf("Копия списка %s", linkedList.copy());
    }
}
