package ru.ipanteev.oop23.hash_table;

import ru.ipanteev.oop23.array_list.ArrayList;

import java.lang.reflect.Array;
import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int INITIAL_CAPACITY = 10;

    private final ArrayList<E>[] table;
    private int size;
    private int modCount;

    public HashTable(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Передана вместимость таблицы %d. Вместимость таблицы должна быть больше или равна 0");
        }

        //noinspection unchecked
        table = (ArrayList<E>[]) Array.newInstance(ArrayList.class, capacity);
    }

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getTableIndex(Object object) {
        return Math.abs(object.hashCode()) % table.length;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        int tableIndex = getTableIndex(o);
        ArrayList<E> row = table[tableIndex];

        if (row == null) {
            return false;
        }

        return row.contains(o);
    }

    private class HashTableIterator implements Iterator<E> {
        private final int modCountAtStart;

        private int currentIndex;
        private int size;
        private Iterator<E> currentArrayIterator;

        public HashTableIterator() {
            modCountAtStart = HashTable.this.modCount;
            this.size = HashTable.this.size;
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Запрошен несуществующий элемент");
            }

            if (modCountAtStart != modCount) {
                throw new ConcurrentModificationException("Коллекция изменилась");
            }

            while (currentArrayIterator == null || !currentArrayIterator.hasNext()) {
                ArrayList<E> tableItem = table[currentIndex];

                if (tableItem != null) {
                    currentArrayIterator = tableItem.iterator();
                }

                currentIndex++;
            }

            size--;
            return currentArrayIterator.next();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }

        Object[] array = new Object[size];
        int arrayFromPosition = 0;

        for (ArrayList<E> row : table) {
            if (row != null) {
                System.arraycopy(row.toArray(), 0, array, arrayFromPosition, row.size());
                arrayFromPosition += row.size();
            }
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            //noinspection unchecked
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);  //в теории типы могут не совпасть и тогда создавать новый массив надо с типом от аргумента
        }

        int arrayFromPosition = 0;

        for (ArrayList<E> row : table) {
            if (row != null) {
                System.arraycopy(row.toArray(), 0, a, arrayFromPosition, row.size());
                arrayFromPosition += row.size();
            }
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        int tableIndex = getTableIndex(e);

        if (table[tableIndex] == null) {
            table[tableIndex] = new ArrayList<>();
        }

        modCount++;
        table[tableIndex].add(e);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ArrayList<E> tableRow = table[getTableIndex(o)];

        if (tableRow == null) {
            return false;
        }

        int deletedIndex = tableRow.indexOf(o);

        if (deletedIndex >= 0) {
            modCount++;
            tableRow.remove(deletedIndex);
            size--;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            if (!add(e)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasDeleted = false;

        for (Object o : c) {
            wasDeleted |= remove(o);
        }

        return wasDeleted;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean wasDeleted = false;

        for (ArrayList<E> tableRow : table) {
            if (tableRow != null) {
                int sizeBeforeDelete = tableRow.size();

                if (tableRow.retainAll(c)) {
                    modCount++;
                    size -= (sizeBeforeDelete - tableRow.size());
                    wasDeleted = true;
                }
            }
        }

        return wasDeleted;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        modCount++;
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (E item : this) {
            stringBuilder.append(item).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
