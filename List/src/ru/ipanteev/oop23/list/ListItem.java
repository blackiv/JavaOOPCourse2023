package ru.ipanteev.oop23.list;

class ListItem<E> {
    private E value;
    private ListItem<E> next;

    public ListItem(E data) {
        this.value = data;
    }

    public ListItem(E value, ListItem<E> next) {
        this.value = value;
        this.next = next;
    }

    public ListItem<E> getNext() {
        return next;
    }

    public void setNext(ListItem<E> next) {
        this.next = next;
    }

    public E get() {
        return value;
    }

    public void set(E value) {
        this.value = value;
    }
}
