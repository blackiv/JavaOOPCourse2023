package ru.ipanteev.oop23.array_list;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayList<E> {
    private static final int INITIALIZE_CAPACITY = 10;
    private E[] items;
    private int size;

    public ArrayList(int capacity) {
        items = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(INITIALIZE_CAPACITY);
    }

    public void add(E item) {
        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = item;
        size++;
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, items.length * 2);
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, size - 1));
        }
    }

    public E get(int index) {
        checkIndexOutOfBounds(index);
        return items[index];
    }

    public void set(int index, E item) {
        checkIndexOutOfBounds(index);
        items[index] = item;
    }

    public void removeAt(int index) {
        checkIndexOutOfBounds(index);

        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;
        size--;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i].toString());
            stringBuilder.append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    public int indexOf(E item) {
        if (item != null) {
            for (int i = 0; i < size; i++) {
                if (items[i].equals(item)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public ArrayList<E> getUniqueList() {
        ArrayList<E> resultList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            E item = items[i];
            if (resultList.indexOf(item) < 0) {
                resultList.add(item);
            }
        }

        return resultList;
    }

    public static ArrayList<Integer> loadFromIntNumbersFile(String fileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            ArrayList<Integer> integerArrayList = new ArrayList<>();

            while (scanner.hasNextInt()) {
                integerArrayList.add(scanner.nextInt());
            }

            return integerArrayList;
        }
    }

    public static void clearEvenNumber(ArrayList<Integer> arrayList) {
        if (arrayList == null || arrayList.size == 0) {
            return;
        }

        int i = 0;

        while (i < arrayList.size) {
            if (arrayList.get(i) % 2 == 0) {
                arrayList.removeAt(i);
                continue;
            }

            i++;
        }
    }
}
