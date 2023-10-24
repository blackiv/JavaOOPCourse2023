package ru.ipanteev.oop23.tree;

public interface NodeVisitor<E> {
    void visit(E nodeValue, int level);
}