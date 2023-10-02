package ru.ipanteev.oop23.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<E> {
    private ListItem<E> head;
    private int size;

    public LinkedList() {
    }

    private void checkEmptyList() {
        if (size == 0) {
            throw new NoSuchElementException("Список пуст");
        }
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, size - 1));
        }
    }

    private ListItem<E> getItemByIndex(int index) {
        ListItem<E> item = head;

        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }

        return item;
    }

    public int getSize() {
        return size;
    }

    public E getFirst() {
        checkEmptyList();

        return head.getValue();
    }

    public E get(int index) {
        checkIndexOutOfBounds(index);
        return getItemByIndex(index).getValue();
    }

    public E set(int index, E value) {
        checkIndexOutOfBounds(index);
        ListItem<E> item = getItemByIndex(index);

        E oldValue = item.getValue();
        item.setValue(value);

        return oldValue;
    }

    public E removeAt(int index) {
        checkIndexOutOfBounds(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);
        ListItem<E> removedItem = previousItem.getNext();
        previousItem.setNext(removedItem.getNext());
        size--;
        return removedItem.getValue();
    }

    public boolean removeByValue(E value) {
        for (ListItem<E> currentItem = head, previousItem = null; currentItem != null; previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (Objects.equals(currentItem.getValue(), value)) {
                if (previousItem == null) {
                    head = head.getNext();
                } else {
                    previousItem.setNext(currentItem.getNext());
                }

                size--;
                return true;
            }
        }

        return false;
    }

    public E removeFirst() {
        checkEmptyList();

        E removedValue = head.getValue();
        head = head.getNext();
        size--;
        return removedValue;
    }

    public void insertFirst(E value) {
        head = new ListItem<>(value, head);
        size++;
    }

    public void insert(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, size));
        }

        if (index == 0) {
            insertFirst(value);
            return;
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);
        previousItem.setNext(new ListItem<>(value, previousItem.getNext()));
        size++;
    }

    public void reverse() {
        if (size < 2) {
            return;
        }

        ListItem<E> nextItem = head.getNext();
        head.setNext(null);
        ListItem<E> currentItem = nextItem;

        while (currentItem != null) {
            nextItem = nextItem.getNext();
            currentItem.setNext(head);
            head = currentItem;
            currentItem = nextItem;
        }
    }

    public LinkedList<E> copy() {
        LinkedList<E> newList = new LinkedList<>();

        if (head == null) {
            return newList;
        }

        newList.head = new ListItem<>(head.getValue());

        for (ListItem<E> originListCurrentItem = head.getNext(), newListCurrentItem = newList.head;
             originListCurrentItem != null;
             originListCurrentItem = originListCurrentItem.getNext(), newListCurrentItem = newListCurrentItem.getNext()) {
            newListCurrentItem.setNext(new ListItem<>(originListCurrentItem.getValue()));
        }

        newList.size = size;
        return newList;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (ListItem<E> item = head; item != null; item = item.getNext()) {
            stringBuilder.append(item.getValue()).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
