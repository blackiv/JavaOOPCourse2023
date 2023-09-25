package ru.ipanteev.oop23.array_list;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int INITIAL_CAPACITY = 10;

    private E[] items;
    private int size;
    private int modCount;

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Передана вместимость списка %d. Вместимость списка должна быть больше или равна 0");
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(INITIAL_CAPACITY);
    }

    public ArrayList(E[] array) {
        this(array.length);
        System.arraycopy(array, 0, items, 0, array.length);
        size = array.length;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            items = Arrays.copyOf(items, INITIAL_CAPACITY);
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Выход за границы массива. Значение индекса %d, индекс должен быть в пределах от 0 до %d",
                    index, size - 1));
        }
    }

    private void checkIndexOutOfBoundsForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Значение индекса %d, индекс должен быть в пределах от 0 до %d", index, size));
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
        private final int initialModCount;

        public ArrayListIterator() {
            initialModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException(String.format("Коллекция не имеет значения по индексу %d, размер коллекции %d",
                        currentIndex + 1, size));
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Коллекция изменилась");
            }

            currentIndex++;
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
        if (a.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, a.getClass()); //в теории типы могут не совпасть и тогда создавать новый массив надо с типом от аргумента
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        add(size, item);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index < 0) {
            return false;
        }

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
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndexOutOfBoundsForAdd(index);
        int sourceArraySize = c.size();

        if (sourceArraySize == 0) {
            return false;
        }

        Object[] sourceArray = c.toArray();

        ensureCapacity(size + sourceArraySize);
        modCount++;

        System.arraycopy(items, index, items, index + sourceArraySize, size - index);

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(sourceArray, 0, items, index, sourceArraySize);
        size += sourceArraySize;
        return true;
    }

    /*удаление всего что совпадает*/
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasDeleted = false;

        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(items[i])) {
                remove(i);
                wasDeleted = true;
            }
        }

        return wasDeleted;
    }

    /*удаление всего что не совпадает*/
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean wasDeleted = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(items[i])) {
                remove(i);
                wasDeleted = true;
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
        Arrays.fill(items, 0, size, null);
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
        E oldItem = items[index];
        items[index] = item;
        return oldItem;
    }

    @Override
    public void add(int index, E item) {
        checkIndexOutOfBoundsForAdd(index);

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
        E removedItem = items[index];

        modCount++;
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;
        size--;
        return removedItem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        //noinspection DataFlowIssue
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
            stringBuilder.append(items[i]).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
