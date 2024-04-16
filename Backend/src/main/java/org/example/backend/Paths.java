package org.example.backend;

import java.util.ArrayList;
import java.util.List;

public class Paths {
    private  List<List<Integer>> forwardPaths = new ArrayList<>();
    private List<Integer> gainOfForwardPaths = new ArrayList<>();
    public List<List<Integer>> getForwardPaths() {
        return forwardPaths;
    }
    public void setForwardPaths(List<List<Integer>> forwardPaths) {
        this.forwardPaths = forwardPaths;
    }
    public List<Integer> getGainOfForwardPaths() {
        return gainOfForwardPaths;
    }
    public void setGainOfForwardPaths(List<Integer> gainOfForwardPaths) {
        this.gainOfForwardPaths = gainOfForwardPaths;
    }

    public void findForwardPaths(double[][] graph) {
        //List<List<Integer>> allPaths = new ArrayList<>();
        dfs(0, new ArrayList<>(), graph, forwardPaths);
    }

    private static void dfs(int node, List<Integer> path, double[][] graph, List<List<Integer>> allPaths) {
        if (node == graph.length - 1) {
            path.add(node);
            allPaths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }
        path.add(node);
        for (int nextNode = node; nextNode < graph.length; nextNode++) {
            if (graph[node][nextNode] !=0 ) {
                dfs(nextNode, path, graph, allPaths);
            }
        }
        path.remove(path.size() - 1);
    }

    public void findGainOfForwardPaths(double[][] graph, List<List<Integer>> forwardPaths) {
        for (List<Integer> path : forwardPaths) {
            int gain = 1;
            for (int i = 0; i < path.size() - 1; i++) {
                gain *= graph[path.get(i)][path.get(i + 1)];
            }
            gainOfForwardPaths.add(gain);
        }
    }

//    public static void main(String[] args) {
//        int[][] graphMatrix = {
//                {0, 10, 5, 0, 0},
//                {0, 0, 1, 25, 0},
//                {0, 0, 0, 6, 1},
//                {0, 0, 0, 0, 20},
//                {0, 0, 0, 0, 0}
//        };
//        forwardPaths = findForwardPaths(graphMatrix);
//        gainOfForwardPaths = findGainOfForwardPaths(graphMatrix, forwardPaths);
//        System.out.println("Forward paths from first node to last node:");
//        for (List<Integer> path : forwardPaths) {
//            System.out.println(path);
//            System.out.println("Gain: " + gainOfForwardPaths.get(forwardPaths.indexOf(path)));
//        }
//    }
}
