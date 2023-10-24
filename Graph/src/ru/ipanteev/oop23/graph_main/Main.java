package ru.ipanteev.oop23.graph_main;

import ru.ipanteev.oop23.graph.Graph;

public class Main {
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
        Graph graph = new Graph(new int[][]{
                {0, 1, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 1},
                {0, 1, 1, 0, 1, 1},
                {0, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 0},
        });

        System.out.println("Печать графа обходом в ширину");
        StringBuilder stringBuilder = new StringBuilder().append('[');

        graph.bypassInWidth(nodeIndex -> stringBuilder.append(nodeIndex).append(", "));

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');
        System.out.println(stringBuilder);

        System.out.println("Печать графа обходом в глубину");
        stringBuilder.delete(1, stringBuilder.length());

        graph.bypassInDepth(nodeIndex -> stringBuilder.append(nodeIndex).append(", "));

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');
        System.out.println(stringBuilder);

        System.out.println("Печать графа обходом в глубину рекурсивно");
        stringBuilder.delete(1, stringBuilder.length());

        graph.bypassInDepthRecursively(nodeIndex -> stringBuilder.append(nodeIndex).append(", "));

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');
        System.out.println(stringBuilder);
    }
}
