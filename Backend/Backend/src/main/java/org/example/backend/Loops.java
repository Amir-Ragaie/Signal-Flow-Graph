package org.example.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Loops {

    private double[][] graph;

    private  List<SingleLoop> loopsInGraph;

    private List<Set<Integer>> NonTouchingLoops;

    public Loops(double[][] graph) {
        this.graph = graph;
        this.loopsInGraph = new ArrayList<>();
        findAllDistinctLoopPaths(graph);
        this.NonTouchingLoops = new ArrayList<>();
        getTwoNonTouchingLoops(loopsInGraph);
    }
    private boolean isTouching(List<Integer> path1, List<Integer> path2){

        for (int currNode : path1) {
            for (int nextNode : path2) {
                if (currNode == nextNode) {
                    return true;
                }

            }
        }
        return false;
    }
    private List<Set<Integer>> getTwoNonTouchingLoops(List<SingleLoop> loopsInGraph){
        List<Set<Integer>>  nonTouchingLoopsIndecies = new ArrayList<>();
        for (int currloopidx = 0 ; currloopidx < loopsInGraph.size() ; currloopidx++) {

            for(int nextloopidx = currloopidx ; nextloopidx < loopsInGraph .size() ; nextloopidx ++){

                if(!isTouching(loopsInGraph.get(currloopidx).getLoopPath(),loopsInGraph.get(nextloopidx).getLoopPath())){
                    Set<Integer> twoNonTouching = new HashSet<>();
                    twoNonTouching.add(currloopidx);
                    twoNonTouching.add(nextloopidx);
                    nonTouchingLoopsIndecies.add(twoNonTouching);
                }

            }
        }
        this.NonTouchingLoops = nonTouchingLoopsIndecies;
        return nonTouchingLoopsIndecies;
    }
    private double getGain(double [][] graph, List<Integer> pathOfLoop){
        double gain = 1;
        for (int i = 0; i < pathOfLoop.size()-1; i++) {
            int currentNode = pathOfLoop.get(i), nextNode= pathOfLoop.get(i+1);
            gain *= graph[currentNode][nextNode];
        }
        return gain;
    }

    // Function to find all distinct loop paths in a graph
    private List<List<Integer>> findAllDistinctLoopPaths(double[][] graph) {
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
        List<List<Integer>> CompleteLoopPaths = new ArrayList<>();
        for (List<Integer> loopPath : distinctLoopPaths) {

            loopPath.add(loopPath.get(0));//add the first node to the end of the loop
            SingleLoop loopFound = new SingleLoop(CompleteLoopPaths.size(), getGain(graph, loopPath), loopPath);
            loopsInGraph.add(loopFound);
            CompleteLoopPaths.add(loopPath);
        }


        return CompleteLoopPaths;
    }

    private void DFS(double[][] graph, int v, boolean[] visited, Set<Integer> path, List<List<Integer>> loopPaths, int startNode) {
        visited[v] = true;
        path.add(v);

        for (int i = 0; i < graph.length; i++) {
            if (graph[v][i] != 0) { // there is a connection
                if (!visited[i]) {
                    DFS(graph, i, visited, path, loopPaths, startNode);
                } else if (i == startNode && path.size() > 1) {
                    // Found a loop
                    List<Integer> loopPath = new ArrayList<>(path);
                    loopPaths.add(loopPath);
                }
            }
        }

        // Check for loop after visiting all neighbors
        if (v == startNode && path.size() > 1) {
            path.add(v);
            List<Integer> loopPath = new ArrayList<>(path);
            loopPaths.add(loopPath);
        }

        path.remove(v); // Backtrack
        visited[v] = false; // Correctly remove the visited node
    }

    public double[][] getGraph() {
        return graph;
    }

    public void setGraph(double[][] graph) {
        this.graph = graph;
    }
    public List<SingleLoop> getLoopsInGraph() {
        return loopsInGraph;
    }

    public void setLoopsInGraph(List<SingleLoop> loopsInGraph) {
        this.loopsInGraph = loopsInGraph;
    }
    public void setNonTouchingLoops(List<Set<Integer>> NonTouchingLoops) {
        this.NonTouchingLoops = NonTouchingLoops;
    }
    public List<Set<Integer>> getNonTouchingLoops() {
        return NonTouchingLoops;
    }

}
