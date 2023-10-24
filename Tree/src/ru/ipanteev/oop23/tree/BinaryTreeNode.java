package ru.ipanteev.oop23.tree;

class BinaryTreeNode<E> {
    private BinaryTreeNode<E> left;
    private BinaryTreeNode<E> right;

    private final E value;

    public BinaryTreeNode(E value) {
        this.value = value;
    }

    public BinaryTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<E> left) {
        this.left = left;
    }

    public BinaryTreeNode<E> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<E> right) {
        this.right = right;
    }

    public E getValue() {
        return value;
    }

    public int getChildrenCount() {
        int count = 0;

        if (left != null) {
            count++;
        }

        if (right != null) {
            count++;
        }

        return count;
    }
}
