package org.example.backend;

import java.util.List;

public class MainForTesting {
    public static void main(String[] args) {
        double[][] graphMatrix = {
                {0, 10, 5, 0, 0},
                {0, 0, 1, 25, 0},
                {0, 0, 0, 6, 1},
                {0, 0, 0, 0, 20},
                {0, 0, 0, 0, 0}
        };
        Paths paths = new Paths();
        paths.findForwardPaths(graphMatrix);
        paths.findGainOfForwardPaths(graphMatrix, paths.getForwardPaths());
        System.out.println("Forward paths from first node to last node:");
        for (List<Integer> path : paths.getForwardPaths()) {
            System.out.println(path);
            System.out.println("Gain: " + paths.getGainOfForwardPaths().get(paths.getForwardPaths().indexOf(path)));
        }

    }
}
