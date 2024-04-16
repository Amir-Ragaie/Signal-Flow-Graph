package org.example.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Loops {
    private static void DFS(int[][] graph, int v, boolean[] visited, Set<Integer> path, List<List<Integer>> loopPaths, int startNode) {
        visited[v] = true;
        path.add(v);

        for (int i = 0; i < graph.length; i++) {
            if (graph[v][i] > 0) {
                if (!visited[i]) {
                    DFS(graph, i, visited, path, loopPaths, startNode);
                } else if (i == startNode && path.size() > 2) {
                    // Found a loop
                    List<Integer> loopPath = new ArrayList<>(path);
                    loopPaths.add(loopPath);
                }
            }
        }

        // Check for loop after visiting all neighbors
        if (v == startNode && path.size() > 2) {
            List<Integer> loopPath = new ArrayList<>(path);
            loopPaths.add(loopPath);
        }

        path.remove(v); // Backtrack
        visited[v] = false; // Correctly remove the visited node
    }

    // Function to find all distinct loop paths in a graph
// Function to find all distinct loop paths in a graph
    public static List<List<Integer>> findAllDistinctLoopPaths(int[][] graph) {
        int V = graph.length;
        List<List<Integer>> loopPaths = new ArrayList<>();
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            Set<Integer> path = new HashSet<>();
            DFS(graph, i, visited, path, loopPaths, i);
        }

        // Remove duplicate loop paths
        List<List<Integer>> distinctLoopPaths = new ArrayList<>();
        for (List<Integer> loopPath : loopPaths) {
            if (!distinctLoopPaths.contains(loopPath)) {
                distinctLoopPaths.add(loopPath);
            }
        }

        return distinctLoopPaths;
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0}
        };

        List<List<Integer>> loopPaths = findAllDistinctLoopPaths(adjacencyMatrix);

        if (loopPaths.isEmpty()) {
            System.out.println("No distinct loop paths found in the graph.");
        } else {
            System.out.println("Distinct loop paths found in the graph:");
            for (List<Integer> loopPath : loopPaths) {
                System.out.println(loopPath);
            }
        }
    }
}
