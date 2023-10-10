package ru.ipanteev.oop23.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@SuppressWarnings("unused")
public class BinarySearchTree<E> {
    private BinaryTreeNode<E> root;
    private final Comparator<E> comparator;
    private int nodesCount;

    public BinarySearchTree() {
        comparator = getComparableComparator();
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = Objects.requireNonNullElseGet(comparator, this::getComparableComparator);
    }

    private Comparator<E> getComparableComparator() {
        return (o1, o2) -> {
            if (o1 == o2) {
                return 0;
            }

            if (o2 == null) {
                return 1;
            }

            if (o1 == null) {
                return -1;
            }

            //noinspection unchecked
            return ((Comparable<E>) o1).compareTo(o2);
        };
    }

    public int getNodesCount() {
        return nodesCount;
    }

    public boolean add(E value) {
        if (root == null) {
            root = new BinaryTreeNode<>(value);
            nodesCount++;
            return true;
        }

        BinaryTreeNode<E> currentNode = root;

        while (true) {
            int compareResult = comparator.compare(value, currentNode.getValue());

            if (compareResult < 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(new BinaryTreeNode<>(value));
                    break;
                }

                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(new BinaryTreeNode<>(value));
                    break;
                }

                currentNode = currentNode.getRight();
            }
        }

        nodesCount++;
        return true;
    }

    private record NodeWithParent<T>(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
    }

    private NodeWithParent<E> findNode(E value) {
        BinaryTreeNode<E> currentNode = root;
        BinaryTreeNode<E> parentNode = null;

        while (currentNode != null) {
            int compareResult = comparator.compare(value, currentNode.getValue());

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

    public boolean contains(E value) {
        return findNode(value).node != null;
    }

    private void removeNodeFromParent(BinaryTreeNode<E> removedNode, BinaryTreeNode<E> parent) {
        BinaryTreeNode<E> replacingNode;

        switch (removedNode.childrenCount()) {
            case 1 -> replacingNode = removedNode.getLeft() != null ? removedNode.getLeft() : removedNode.getRight();
            case 2 -> {
                BinaryTreeNode<E> leftmostNode = removedNode.getRight();
                BinaryTreeNode<E> leftmostNodeParentNode = removedNode;

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

    public boolean remove(E value) {
        NodeWithParent<E> foundNode = findNode(value);

        if (foundNode.node == null) {
            return false;
        }

        removeNodeFromParent(foundNode.node, foundNode.parent);
        nodesCount--;
        return true;
    }

    private record NodeWithLevel<T>(BinaryTreeNode<T> node, int level) {
    }

    public void doTreeTraversalInWidth(NodeVisitor<E> visitor) {
        if (root == null) {
            return;
        }

        Queue<NodeWithLevel<E>> queue = new LinkedList<>();
        queue.add(new NodeWithLevel<>(root, 1));

        while (!queue.isEmpty()) {
            NodeWithLevel<E> processedNode = queue.remove();
            visitor.visit(processedNode.node.getValue(), processedNode.level);
            int childLevel = processedNode.level + 1;

            BinaryTreeNode<E> leftChildNode = processedNode.node.getLeft();

            if (leftChildNode != null) {
                queue.add(new NodeWithLevel<>(leftChildNode, childLevel));
            }

            BinaryTreeNode<E> rightChildNode = processedNode.node.getRight();

            if (rightChildNode != null) {
                queue.add(new NodeWithLevel<>(rightChildNode, childLevel));
            }
        }
    }

    public void doTreeTraversalInDepth(NodeVisitor<E> visitor) {
        if (root == null) {
            return;
        }

        LinkedList<NodeWithLevel<E>> stack = new LinkedList<>();
        stack.addFirst(new NodeWithLevel<>(root, 1));

        while (!stack.isEmpty()) {
            NodeWithLevel<E> node = stack.removeFirst();
            visitor.visit(node.node.getValue(), node.level);

            int childLevel = node.level + 1;

            BinaryTreeNode<E> addedNode = node.node.getRight();

            if (addedNode != null) {
                stack.addFirst(new NodeWithLevel<>(addedNode, childLevel));
            }

            addedNode = node.node.getLeft();

            if (addedNode != null) {
                stack.addFirst(new NodeWithLevel<>(addedNode, childLevel));
            }
        }
    }

    private void visit(BinaryTreeNode<E> node, int level, NodeVisitor<E> visitor) {
        if (node == null) {
            return;
        }

        visitor.visit(node.getValue(), level);

        level++;
        visit(node.getLeft(), level, visitor);
        visit(node.getRight(), level, visitor);
    }

    public void doTreeTraversalInDepthRecursive(NodeVisitor<E> visitor) {
        visit(root, 1, visitor);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        doTreeTraversalInWidth((nodeValue, level) -> stringBuilder.append(nodeValue).append(" (").append(level).append("), "));
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}

