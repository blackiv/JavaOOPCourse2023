package ru.ipanteev.oop23.tree;

class BinaryTreeNode<T> {
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;

    private final T value;

    public BinaryTreeNode(T value) {
        this.value = value;
    }

    public BinaryTreeNode(T value, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public int childCount() {
        int result = 0;

        if (left != null) {
            result++;
        }

        if (right != null) {
            result++;
        }

        return result;
    }
}
