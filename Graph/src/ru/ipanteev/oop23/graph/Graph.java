package ru.ipanteev.oop23.graph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.IntConsumer;

public class Graph {
    private final int[][] edgesMatrix;

    public Graph(int[][] edgesMatrix) {
        if (edgesMatrix.length != edgesMatrix[0].length) {
            throw new IllegalArgumentException("Переданная матрица должна быть квадратной.");
        }

        this.edgesMatrix = edgesMatrix;
    }

    public void bypassInWidth(IntConsumer consumer) {
        if (edgesMatrix.length == 0) {
            return;
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] isVisited = new boolean[edgesMatrix.length];

        for (int i = 0; i < isVisited.length; i++) {
            if (!isVisited[i]) {
                queue.add(i);
                isVisited[i] = true;

                while (!queue.isEmpty()) {
                    int nodeIndex = queue.remove();
                    int[] nodeEdges = edgesMatrix[nodeIndex];

                    consumer.accept(nodeIndex);

                    for (int j = 0; j < nodeEdges.length; j++) {
                        if (nodeEdges[j] == 1 && !isVisited[j]) {
                            queue.add(j);
                            isVisited[j] = true;
                        }
                    }
                }
            }
        }
    }

    public void bypassInDepth(IntConsumer consumer) {
        if (edgesMatrix.length == 0) {
            return;
        }

        Deque<Integer> stack = new LinkedList<>();
        boolean[] isVisited = new boolean[edgesMatrix.length];

        for (int i = 0; i < isVisited.length; i++) {
            if (!isVisited[i]) {
                stack.addFirst(i);
                isVisited[i] = true;

                while (!stack.isEmpty()) {
                    int nodeIndex = stack.removeFirst();
                    int[] nodeEdges = edgesMatrix[nodeIndex];

                    consumer.accept(nodeIndex);

                    for (int j = nodeEdges.length - 1; j >= 0; j--) {
                        if (nodeEdges[j] == 1 && !isVisited[j]) {
                            stack.addFirst(j);
                            isVisited[j] = true;
                        }
                    }
                }
            }
        }
    }

    private void visit(int nodeIndex, boolean[] isVisited, IntConsumer consumer) {
        if (isVisited[nodeIndex]) {
            return;
        }

        isVisited[nodeIndex] = true;
        consumer.accept(nodeIndex);
        int[] nodeEdges = edgesMatrix[nodeIndex];

        for (int j = 0; j < nodeEdges.length; j++) {
            if (nodeEdges[j] == 1 && !isVisited[j]) {
                visit(j, isVisited, consumer);
            }
        }
    }

    public void bypassInDepthRecursively(IntConsumer consumer) {
        boolean[] isVisited = new boolean[edgesMatrix.length];
        visit(0, isVisited, consumer);
    }
}
