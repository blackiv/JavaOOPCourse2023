package ru.ipanteev.oop23.array_list;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int INITIALIZE_CAPACITY = 10;
    private E[] items;
    private int size = 0;

    private int modCount = 0;

    public ArrayList(int capacity) {
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(INITIALIZE_CAPACITY);
    }

    public ArrayList(E[] array) {
        this(array.length);
        System.arraycopy(array, 0, items, 0, array.length);
        size = array.length;
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, items.length * 2);
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, Math.max(capacity, items.length * 2));
        }
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, size - 1));
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int modCount;

        public ArrayListIterator() {
            modCount = ArrayList.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            currentIndex++;

            if (currentIndex >= size) {
                throw new NoSuchElementException(String.format("Коллекция не имеет значения по индексу %d, размер коллекции %d",
                        currentIndex, size));
            }

            if (modCount != ArrayList.this.modCount) {
                throw new ConcurrentModificationException("Коллекция изменилась");
            }

            return items[currentIndex];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(a.getClass() != items.getClass()){
            throw new IllegalArgumentException("Копирование разрешено только в однотипные массивы");
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, a, 0, Math.min(size, a.length));
        return a;
    }

    @Override
    public boolean add(E item) {
        if (size >= items.length) {
            increaseCapacity();
        }

        modCount++;
        items[size] = item;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index < 0)
            return false;

        remove(index);
        return true;
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
        //noinspection unchecked
        E[] sourceArray = (E[]) c.toArray();
        int sourceArraySize = sourceArray.length;

        if (sourceArraySize == 0) {
            return false;
        }

        ensureCapacity(size + sourceArraySize);
        modCount++;

        System.arraycopy(sourceArray, 0, items, size, sourceArraySize);
        size += sourceArraySize;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndexOutOfBounds(index);
        //noinspection unchecked
        E[] sourceArray = (E[])c.toArray();
        int sourceArraySize = sourceArray.length;

        if (sourceArraySize == 0) {
            return false;
        }

        ensureCapacity(size + sourceArraySize);
        modCount++;

        System.arraycopy(items, index, items, index + sourceArraySize, size - index);
        System.arraycopy(sourceArray, 0, items, index, sourceArraySize);
        size += sourceArraySize;
        return true;
    }

    /*удаление всего что совпадает*/
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;

        for (Object o : c) {
            for (int i = indexOf(o, 0); i >= 0; i = indexOf(o, i)) {
                remove(i);
                result = true;
            }
        }

        return result;
    }

    /*удаление всего что не совпадает*/
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;

        for (int i = 0; i < size; ) {
            if (!c.contains(items[i])) {
                remove(i);
                result = true;
            } else {
                i++;
            }
        }

        return result;
    }

    @Override
    public void clear() {
        modCount++;

        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndexOutOfBounds(index);
        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndexOutOfBounds(index);
        modCount++;
        items[index] = item;
        return item;
    }

    @Override
    public void add(int index, E item) {
        checkIndexOutOfBounds(index);
        if (size >= items.length) {
            increaseCapacity();
        }

        modCount++;
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndexOutOfBounds(index);
        E deleteValue = items[index];

        modCount++;
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;
        size--;
        return deleteValue;
    }

    private int indexOf(Object o, int start) {
        if (o == null) {
            for (int i = start; i < size; i++) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < size; i++) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int indexOf(Object o) {
        return indexOf(o, 0);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size; i >= 0; i--) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size; i >= 0; i--) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i]);
            stringBuilder.append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }


}
