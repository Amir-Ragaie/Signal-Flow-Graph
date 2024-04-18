package org.example.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service {
    List<List<Integer>> forwardPaths = new ArrayList<>();
    List<Integer> gainOfForwardPaths = new ArrayList<>();
    List<List<Integer>> loopsInGraph;
    List<Set<Integer>> NonTouchingLoops;
    ArrayList<Double> deltas = new ArrayList<>();
    double delta;
    double transferFunction;

    public double getTransferFunction() {
        return transferFunction;
    }

    public void setTransferFunction(double transferFunction) {
        this.transferFunction = transferFunction;
    }

    public List<Set<Integer>> getNonTouchingLoops() {
        return NonTouchingLoops;
    }

    public void setNonTouchingLoops(List<Set<Integer>> nonTouchingLoops) {
        NonTouchingLoops = nonTouchingLoops;
    }

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

    public List<List<Integer>> getLoopsInGraph() {
        return loopsInGraph;
    }

    public void setLoopsInGraph(List<List<Integer>> loopsInGraph) {
        this.loopsInGraph = loopsInGraph;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public ArrayList<Double> getDeltas() {
        return deltas;
    }

    public void setDeltas(ArrayList<Double> deltas) {
        this.deltas = deltas;
    }



    public Service(List<List<Integer>> forwardPaths, List<Integer> gainOfForwardPaths, List<List<Integer>> loopsInGraph, double delta, ArrayList<Double> deltas, List<Set<Integer>> nonTouchingLoops) {
        this.forwardPaths = forwardPaths;
        this.gainOfForwardPaths = gainOfForwardPaths;
        this.loopsInGraph = loopsInGraph;
        this.delta = delta;
        this.deltas = deltas;
        this.NonTouchingLoops = nonTouchingLoops;
    }
}
