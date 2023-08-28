package ru.ipanteev.oop23.list;

import java.util.Objects;

public class LinkedList<E> {
    private ListItem<E> head;
    private int count;

    public LinkedList() {
    }

    public int getSize() {
        return count;
    }

    public E getFirstValue() {
        if (head == null) {
            throw new NullPointerException("Список пуст");
        }

        return head.getData();
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, count - 1));
        }
    }

    private ListItem<E> getItemByIndex(int index) {
        checkIndexOutOfBounds(index);
        ListItem<E> itemByIndex = head;

        for (int i = 0; i < index; i++) {
            itemByIndex = itemByIndex.getNext();
        }

        return itemByIndex;
    }

    public E getValue(int index) {
        return getItemByIndex(index).getData();
    }

    public E setValue(E data, int index) {
        ListItem<E> itemByIndex = getItemByIndex(index);

        E itemPreviousValue = itemByIndex.getData();
        itemByIndex.setData(data);

        return itemPreviousValue;
    }

    public E removeAt(int index) {
        ListItem<E> removeItem;

        if (index > 0) {
            ListItem<E> item = getItemByIndex(index - 1);
            removeItem = item.getNext();
            item.setNext(removeItem.getNext());
        } else {
            removeItem = head;
            head = removeItem.getNext();
        }

        count--;
        return removeItem.getData();
    }

    public boolean removeByValue(E value) {
        for (ListItem<E> p = head, prev = null; p != null; prev = p, p = p.getNext()) {
            if (Objects.equals(p.getData(), value)) {
                if (prev == null) {
                    head = head.getNext();
                } else {
                    prev.setNext(p.getNext());
                }

                return true;
            }
        }

        return false;
    }

    public E removeFirst() {
        ListItem<E> removeItem = head;
        head = head.getNext();
        return removeItem.getData();
    }

    public void insertFirst(ListItem<E> item) {
        if (head != null) {
            item.setNext(head);
        }

        head = item;
        count++;
    }

    public void insert(ListItem<E> item, int index) {
        if (index > 0) {
            ListItem<E> itemBefore = getItemByIndex(index - 1);
            item.setNext(itemBefore.getNext());
            itemBefore.setNext(item);
            count++;
        } else {
            insertFirst(item);
        }
    }

    public void reverse() {
        ListItem<E> nextList = head.getNext();
        head.setNext(null);
        ListItem<E> currentListItem = nextList;

        while (currentListItem != null) {
            nextList = nextList.getNext();
            insertFirst(currentListItem);
            currentListItem = nextList;
        }
    }

    public LinkedList<E> copy() {
        LinkedList<E> newList = new LinkedList<>();
        newList.insertFirst(new ListItem<>(head.getData(), head.getNext()));

        for (ListItem<E> p = newList.head.getNext(), prev = newList.head; p != null; prev = p, p = p.getNext()) {
            prev.setNext(new ListItem<>(p.getData(), p.getNext()));
        }

        return newList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (ListItem<E> p = head; p != null; p = p.getNext()) {
            stringBuilder.append(p.getData()).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}
