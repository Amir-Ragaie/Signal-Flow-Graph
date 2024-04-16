package org.example.backend;

import java.util.ArrayList;
import java.util.List;

public class Paths {
    public static List<List<Integer>> findForwardPaths(int[][] graph) {
        List<List<Integer>> allPaths = new ArrayList<>();
        dfs(0, new ArrayList<>(), graph, allPaths);
        return allPaths;
    }

    private static void dfs(int node, List<Integer> path, int[][] graph, List<List<Integer>> allPaths) {
        if (node == graph.length - 1) {
            path.add(node);
            allPaths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }
        path.add(node);
        for (int nextNode = 0; nextNode < graph.length; nextNode++) {
            if (graph[node][nextNode] >0 ) {
                dfs(nextNode, path, graph, allPaths);
            }
        }
        path.remove(path.size() - 1);
    }

//    public static void main(String[] args) {
//        int[][] graphMatrix = {
//                {0, 10, 5, 0, 0},
//                {0, 0, 1, 25, 0},
//                {0, 0, 0, 6, 1},
//                {0, 0, 0, 0, 20},
//                {0, 0, 0, 0, 0}
//        };
//
//        List<List<Integer>> forwardPaths = findForwardPaths(graphMatrix);
//        System.out.println("Forward paths from first node to last node:");
//        for (List<Integer> path : forwardPaths) {
//            System.out.println(path);
//        }
//    }
}
