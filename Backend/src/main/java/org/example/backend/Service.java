package org.example.backend;

public class Service {
    Paths paths;
    Loops loops;
    Deltas deltas;

    public Service(double [][]graph) {
        paths = new Paths();
        loops = new Loops(graph);
        paths.handlePaths(graph);
        deltas = new Deltas(loops, paths.getForwardPaths(),graph);
    }
}
