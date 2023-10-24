package ru.ipanteev.oop23.tree;

import java.util.*;

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
        return (value1, value2) -> {
            if (value1 == value2) {
                return 0;
            }

            if (value2 == null) {
                return 1;
            }

            if (value1 == null) {
                return -1;
            }

            //noinspection unchecked
            return ((Comparable<E>) value1).compareTo(value2);
        };
    }

    public int getElementsCount() {
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

    private NodeWithParent<E> findPair(E value) {
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
        return findPair(value).node != null;
    }

    private void removeNodeFromParent(BinaryTreeNode<E> removedNode, BinaryTreeNode<E> parent) {
        BinaryTreeNode<E> replacingNode;

        switch (removedNode.getChildrenCount()) {
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
        NodeWithParent<E> foundPair = findPair(value);

        if (foundPair.node == null) {
            return false;
        }

        removeNodeFromParent(foundPair.node, foundPair.parent);
        nodesCount--;
        return true;
    }

    private record NodeWithLevel<T>(BinaryTreeNode<T> node, int level) {
    }

    public void bypassInWidth(NodeVisitor<E> visitor) {
        if (root == null) {
            return;
        }

        Queue<NodeWithLevel<E>> queue = new LinkedList<>();
        queue.add(new NodeWithLevel<>(root, 1));

        while (!queue.isEmpty()) {
            NodeWithLevel<E> processedNodePair = queue.remove();
            BinaryTreeNode<E> processedNode = processedNodePair.node;
            int nodeLevel = processedNodePair.level;
            visitor.visit(processedNode.getValue(), nodeLevel);
            int childLevel = nodeLevel + 1;

            BinaryTreeNode<E> leftChildNode = processedNode.getLeft();

            if (leftChildNode != null) {
                queue.add(new NodeWithLevel<>(leftChildNode, childLevel));
            }

            BinaryTreeNode<E> rightChildNode = processedNode.getRight();

            if (rightChildNode != null) {
                queue.add(new NodeWithLevel<>(rightChildNode, childLevel));
            }
        }
    }

    public void bypassInDepth(NodeVisitor<E> visitor) {
        if (root == null) {
            return;
        }

        Deque<NodeWithLevel<E>> stack = new LinkedList<>();
        stack.addFirst(new NodeWithLevel<>(root, 1));

        while (!stack.isEmpty()) {
            NodeWithLevel<E> processedNodePair = stack.removeFirst();
            visitor.visit(processedNodePair.node.getValue(), processedNodePair.level);

            int childLevel = processedNodePair.level + 1;

            BinaryTreeNode<E> addedNode = processedNodePair.node.getRight();

            if (addedNode != null) {
                stack.addFirst(new NodeWithLevel<>(addedNode, childLevel));
            }

            addedNode = processedNodePair.node.getLeft();

            if (addedNode != null) {
                stack.addFirst(new NodeWithLevel<>(addedNode, childLevel));
            }
        }
    }

    private void visit(BinaryTreeNode<E> processedNode, int level, NodeVisitor<E> visitor) {
        if (processedNode == null) {
            return;
        }

        visitor.visit(processedNode.getValue(), level);

        int childrenLevel = level + 1;
        visit(processedNode.getLeft(), childrenLevel, visitor);
        visit(processedNode.getRight(), childrenLevel, visitor);
    }

    public void bypassInDepthRecursively(NodeVisitor<E> visitor) {
        visit(root, 1, visitor);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        bypassInWidth((nodeValue, level) -> stringBuilder.append(nodeValue).append(" (").append(level).append("), "));
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}

