package org.example.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Deltas {
    double delta;
    ArrayList<Double> deltas = new ArrayList<>();

    public Deltas(Loops loops, List<List<Integer>> forwardPaths, double[][] graph) {
        delta = mainDeltaSetting(loops);
        deltas = deltasSetting(forwardPaths,graph);
    }

    public double getDelta() {
        return delta;
    }

    public ArrayList<Double> getDeltas() {
        return deltas;
    }

    public double mainDeltaSetting(Loops loops){
        double delta = 1;
        for (SingleLoop singleLoop : loops.getLoopsInGraph()){
            delta -= singleLoop.getLoopGain();
            System.out.println("current loop gain is "+singleLoop.getLoopGain()+" delta is "+delta);
        }
        for (int i=0 ; i<loops.getNonTouchingLoops().size();i++){
            double gainMultiply = 1.0;
            for(Integer loopIndex : loops.getNonTouchingLoops().get(i)){
                gainMultiply *= loops.getLoopsInGraph().get(loopIndex).getLoopGain();
            }
//            if (gainMultiply != 1.0)
                delta += gainMultiply;
        }
        return delta;
    }
    public ArrayList<Double> deltasSetting(List<List<Integer>> forwardPaths, double[][] graph){
        ArrayList<Double> deltas = new ArrayList<>();
        for (List<Integer> list : forwardPaths){
            int rows = graph.length;
            int cols = graph[0].length;
            double[][] newGraph = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    newGraph[i][j] = graph[i][j]; // Initialize to default value (0.0 in this case)
                }
            }
            for (Integer node : list){
                for (int i = 0; i < graph.length; i++) {
                    newGraph[node][i] = 0; // Clearing outgoing edges
                    newGraph[i][node] = 0; // Clearing ingoing edges
                }
            }
            Loops loops = new Loops(newGraph);
            deltas.add(this.mainDeltaSetting(loops));
        }
        return deltas;
    }
}

