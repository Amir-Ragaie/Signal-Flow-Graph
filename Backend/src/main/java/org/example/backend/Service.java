package org.example.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service {
    List<List<Integer>> forwardPaths = new ArrayList<>();
    List<Integer> gainOfForwardPaths = new ArrayList<>();
    List<SingleLoop> loopsInGraph;
    List<Set<Integer>> NonTouchingLoops;
    double delta;
    ArrayList<Double> deltas = new ArrayList<>();

    public Service(List<List<Integer>> forwardPaths, List<Integer> gainOfForwardPaths, List<SingleLoop> loopsInGraph, double delta, ArrayList<Double> deltas) {
        this.forwardPaths = forwardPaths;
        this.gainOfForwardPaths = gainOfForwardPaths;
        this.loopsInGraph = loopsInGraph;
        this.delta = delta;
        this.deltas = deltas;
    }
}
