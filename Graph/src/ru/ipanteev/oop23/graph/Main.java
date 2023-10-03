package ru.ipanteev.oop23.graph;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public interface NodeVisitor {
        void visit(int nodeIndex);
    }
    public static void doGraphTraversalByLevel(int[][] graph, NodeVisitor visitor) {
        if (graph.length == 0) {
            return;
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visitedNodes = new boolean[graph.length];

        for (int i = 0; i < visitedNodes.length; i++) {
            if (!visitedNodes[i]) {
                queue.add(i);
                visitedNodes[i] = true;

                while (!queue.isEmpty()) {
                    int nodeIndex = queue.remove();
                    int[] nodeEdges = graph[nodeIndex];

                    visitor.visit(nodeIndex);

                    for (int j = i; j < nodeEdges.length; j++) {
                        if (nodeEdges[j] == 1 && !visitedNodes[j]) {
                            queue.add(j);
                            visitedNodes[j] = true;
                        }
                    }
                }
            }
        }
    }

    public static void doGraphTraversalInDepth(int[][] graph, NodeVisitor visitor) {
        if (graph.length == 0) {
            return;
        }

        LinkedList<Integer> stack = new LinkedList<>();
        boolean[] visitedNodes = new boolean[graph.length];

        for (int i = 0; i < visitedNodes.length; i++) {
            if (!visitedNodes[i]) {
                stack.addFirst(i);
                visitedNodes[i] = true;

                while (!stack.isEmpty()) {
                    int nodeIndex = stack.removeFirst();
                    int[] nodeEdges = graph[nodeIndex];

                    visitor.visit(nodeIndex);

                    for (int j = i + 1; j < nodeEdges.length; j++) {
                        if (nodeEdges[j] == 1 && !visitedNodes[j]) {
                            stack.addFirst(j);
                            visitedNodes[j] = true;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        /*     1
         *    / \
         *   2   3
         *    \ /|
         *     4 |
         *     /\|
         *    5  6
         *
         * */
        int[][] graph = new int[][]{
                {0, 1, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 1},
                {0, 1, 1, 0, 1, 1},
                {0, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 0},
        };

        System.out.println("Печать графа обходом в ширину");
        StringBuilder stringBuilder = new StringBuilder().append('[');

        doGraphTraversalByLevel(graph, nodeIndex -> stringBuilder.append(nodeIndex+1).append(", "));

        stringBuilder.delete(stringBuilder.length()-2,stringBuilder.length());
        stringBuilder.append(']');
        System.out.println(stringBuilder);

        System.out.println("Печать графа обходом в глубину");
        stringBuilder.delete(1,stringBuilder.length());

        doGraphTraversalInDepth(graph, nodeIndex -> stringBuilder.append(nodeIndex+1).append(", "));

        stringBuilder.delete(stringBuilder.length()-2,stringBuilder.length());
        stringBuilder.append(']');
        System.out.println(stringBuilder);

    }

}
