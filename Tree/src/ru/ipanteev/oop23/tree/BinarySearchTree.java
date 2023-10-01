package ru.ipanteev.oop23.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T> {
    private BinaryTreeNode<T> root;
    private final Comparator<T> comparator;
    private int nodeCount;

    public BinarySearchTree(Class nodeValueClass) {/*не умеет java typeof(T) надо передавать класс*/
        if (Arrays.stream(nodeValueClass.getInterfaces()).noneMatch(aClass -> aClass == Comparable.class)) {
            throw new IllegalArgumentException("Для хранения элементов без интерфейса Comparable используйте конструктор BinarySearchTree(Comparator<T> comparator)");
        }

        comparator = (o1, o2) -> ((Comparable<T>) o1).compareTo(o2);
    }

    public BinarySearchTree(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Аргумент comparator не может быть пустым");
        }

        this.comparator = comparator;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public boolean add(T value) {
        if (root == null) {
            root = new BinaryTreeNode<>(value);
        } else {
            BinaryTreeNode<T> parentNode = root;

            int compareResult;

            while (true) {
                compareResult = comparator.compare(value, parentNode.getValue());

                if (compareResult < 0) {
                    if (parentNode.getLeft() == null) {
                        parentNode.setLeft(new BinaryTreeNode<>(value));
                        break;
                    }

                    parentNode = parentNode.getLeft();
                } else {
                    if (parentNode.getRight() == null) {
                        parentNode.setRight(new BinaryTreeNode<>(value));
                        break;
                    }

                    parentNode = parentNode.getRight();
                }
            }
        }
        nodeCount++;
        return true;
    }

    private record NodeWithParent<T>(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
    }

    private NodeWithParent<T> findNode(T value) {
        BinaryTreeNode<T> currentNode = root;
        BinaryTreeNode<T> parentNode = null;

        int compareResult;

        while (currentNode != null) {
            compareResult = comparator.compare(value, currentNode.getValue());

            if (compareResult == 0) {
                break;
            }

            parentNode = currentNode;

            if (compareResult < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        return new NodeWithParent<>(currentNode, parentNode);
    }

    public boolean contains(T value) {
        return findNode(value).node != null;
    }

    private void removeNodeFromParent(BinaryTreeNode<T> removedNode, BinaryTreeNode<T> parent) {
        BinaryTreeNode<T> replacingNode;

        switch (removedNode.childCount()) {
            case 1 -> replacingNode = removedNode.getLeft() != null ? removedNode.getLeft() : removedNode.getRight();
            case 2 -> {
                BinaryTreeNode<T> leftmostNode = removedNode.getRight();
                BinaryTreeNode<T> leftmostNodeParentNode = removedNode;

                while (leftmostNode.getLeft() != null) {
                    leftmostNodeParentNode = leftmostNode;
                    leftmostNode = leftmostNode.getLeft();
                }

                replacingNode = leftmostNode;
                removeNodeFromParent(leftmostNode, leftmostNodeParentNode);

                replacingNode.setLeft(removedNode.getLeft());
                replacingNode.setRight(removedNode.getRight());
            }
            default -> replacingNode = null;
        }

        if (parent == null) {
            root = replacingNode;
        } else {
            if (parent.getLeft() == removedNode) {
                parent.setLeft(replacingNode);
            } else {
                parent.setRight(replacingNode);
            }
        }
    }

    public boolean remove(T value) {
        NodeWithParent<T> foundNode = findNode(value);

        if (foundNode.node == null) {
            return false;
        }

        removeNodeFromParent(foundNode.node, foundNode.parent);
        nodeCount--;
        return true;
    }

    private record NodeWithLevel<T>(BinaryTreeNode<T> node, int level) {
    }

    public void doTreeTraversalByLevel(NodeVisitor<T> visitor) {
        if (root == null) {
            return;
        }

        Queue<NodeWithLevel<T>> queue = new LinkedList<>();
        queue.add(new NodeWithLevel<>(root, 1));

        while (!queue.isEmpty()) {
            NodeWithLevel<T> node = queue.remove();
            visitor.visit(node.node.getValue(), node.level);
            int childLevel = node.level + 1;

            BinaryTreeNode<T> addedNode = node.node.getLeft();

            if (addedNode != null) {
                queue.add(new NodeWithLevel<>(addedNode, childLevel));
            }

            addedNode = node.node.getRight();

            if (addedNode != null) {
                queue.add(new NodeWithLevel<>(addedNode, childLevel));
            }
        }
    }

    public void doTreeTraversalInDepth(NodeVisitor<T> visitor) {
        if (root == null) {
            return;
        }

        LinkedList<NodeWithLevel<T>> stack = new LinkedList<>();
        stack.addFirst(new NodeWithLevel<>(root, 1));

        while (!stack.isEmpty()) {
            NodeWithLevel<T> node = stack.removeFirst();
            visitor.visit(node.node.getValue(), node.level);

            int childLevel = node.level + 1;

            BinaryTreeNode<T> addedNode = node.node.getRight();

            if (addedNode != null) {
                stack.addFirst(new NodeWithLevel<>(addedNode, childLevel));
            }

            addedNode = node.node.getLeft();

            if (addedNode != null) {
                stack.addFirst(new NodeWithLevel<>(addedNode, childLevel));
            }
        }
    }

    public void visit(BinaryTreeNode<T> node, int level, NodeVisitor<T> visitor) {
        if (node == null) {
            return;
        }

        visitor.visit(node.getValue(), level);

        level++;
        visit(node.getLeft(), level, visitor);
        visit(node.getRight(), level, visitor);
    }

    public void doTreeTraversalInDepthRecursive(NodeVisitor<T> visitor) {
        visit(root, 1, visitor);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        doTreeTraversalByLevel((nodeValue, level) -> stringBuilder.append(nodeValue).append(" (").append(level).append("), "));
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}

