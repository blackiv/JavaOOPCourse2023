package ru.ipanteev.oop23.hash_table;

import java.lang.reflect.Array;
import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private final ArrayList<E>[] lists;
    private int size;
    private int modCount;

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(String.format("Передана вместимость таблицы %d. Вместимость таблицы должна быть больше 0", capacity));
        }

        //noinspection unchecked
        lists = new ArrayList[capacity];
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(Object object) {
        if (object == null) {
            return 0;
        }

        return Math.abs(object.hashCode()) % lists.length;
    }

    @Override
    public boolean contains(Object o) {
        int listIndex = getIndex(o);
        ArrayList<E> list = lists[listIndex];

        return list != null && list.contains(o);
    }

    private class HashTableIterator implements Iterator<E> {
        private final int initialModCount;

        private int listIndex;
        private int iterationsLeftCount;
        private Iterator<E> listIterator;

        public HashTableIterator() {
            initialModCount = modCount;
            iterationsLeftCount = size;
        }

        @Override
        public boolean hasNext() {
            return iterationsLeftCount > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Коллекция закончилась");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Коллекция изменилась");
            }

            while (listIterator == null || !listIterator.hasNext()) {
                ArrayList<E> list = lists[listIndex];

                if (list != null) {
                    listIterator = list.iterator();
                }

                listIndex++;
            }

            iterationsLeftCount--;
            return listIterator.next();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int arrayPosition = 0;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                for (E item : list) {
                    array[arrayPosition] = item;
                    arrayPosition++;
                }
            }
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            //noinspection unchecked
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);  // в теории типы могут не совпасть и тогда создавать новый массив надо с типом от аргумента
        }

        int arrayPosition = 0;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                for (E item : list) {
                    //noinspection unchecked
                    a[arrayPosition] = (T) item;
                    arrayPosition++;
                }
            }
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        int listIndex = getIndex(e);

        if (lists[listIndex] == null) {
            lists[listIndex] = new ArrayList<>();
        }

        modCount++;
        lists[listIndex].add(e);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ArrayList<E> list = lists[getIndex(o)];

        if (list == null) {
            return false;
        }

        if (list.remove(o)) {
            modCount++;
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
        boolean wasAdded = false;

        for (E e : c) {
            if (add(e)) {
                wasAdded = true;
            }
        }

        return wasAdded;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasRemoved = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int sizeBeforeRemoving = list.size();
                if (list.removeAll(c)) {
                    wasRemoved = true;
                    size -= sizeBeforeRemoving - list.size();
                }
            }
        }

        if (wasRemoved) {
            modCount++;
        }

        return wasRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean wasRemoved = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int sizeBeforeDelete = list.size();

                if (list.retainAll(c)) {
                    size -= sizeBeforeDelete - list.size();
                    wasRemoved = true;
                }
            }
        }

        if (wasRemoved) {
            modCount++;
        }

        return wasRemoved;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        modCount++;
        Arrays.fill(lists, null);
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
