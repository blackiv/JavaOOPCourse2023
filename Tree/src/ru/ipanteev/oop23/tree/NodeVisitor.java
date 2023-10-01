package ru.ipanteev.oop23.tree;

public interface NodeVisitor<T>{
    void visit(T nodeValue, int level);
}