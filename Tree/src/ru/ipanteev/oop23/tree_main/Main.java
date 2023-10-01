package ru.ipanteev.oop23.tree_main;

import ru.ipanteev.oop23.tree.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> intTree = new BinarySearchTree<>(Integer.class);
        intTree.add(23);
        intTree.add(45);
        intTree.add(45);
        intTree.add(16);
        intTree.add(17);
        intTree.add(22);

        System.out.printf("Имеется дерево элементов %s%n", intTree);
        System.out.println();

        int searchedNumber = 16;
        System.out.printf("Поиск числа %d %n", searchedNumber);

        if (intTree.contains(searchedNumber)) {
            System.out.println("Дерево содержит заданное число");
        } else {
            System.out.println("Дерево не содержит заданное число");
        }

        System.out.println();

        int removedNumber = 45;
        System.out.printf("Удаление числа %d %n", removedNumber);

        if (intTree.remove(removedNumber)) {
            System.out.println("Число удалено из дерева");
        } else {
            System.out.println("Число не удалено из дерева");
        }

        System.out.println();
        System.out.printf("Дерево %s%n", intTree);

        System.out.println("Печать значений дерева обходом в глубину");
        intTree.doTreeTraversalInDepth((nodeValue, level) -> System.out.printf("%d (%d)%n",nodeValue,level));
    }
}
