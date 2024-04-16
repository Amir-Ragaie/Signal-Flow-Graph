package org.example.backend;

import java.util.List;

public class Test_Class {

    public static void main(String[] args) {


//        double[][] adjacencyMatrix = {
//                {0, 5, 0, 0, 0},
//                {0, 0, 10, 0, 0},
//                {-1, 0, 0, -30, 0},
//                {0, 0, 0, 0,24},
//                {74, 0, 0, 0, 0}
//        };
//        double[][] adjacencyMatrix = {
//                {0, 8, 0, 0, 0},
//                {1, 0, 1, 0, 0},
//                {1, 0, 0, 4, 0},
//                {0, 0,0, 0, 20},
//                {0, 0,16, 1, 0}
//        };
        double[][] adjacencyMatrix = {
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0}
        };
//                double[][] adjacencyMatrix = {
//                {1, 0, 0, 0, 0},
//                {0, 2, 0, 0, 0},
//                {5, 0, 0, 5, 0},
//                {0, 0,3, 0, 5},
//                {-3, 0, 0, 0, 0}
//        };
        Loops testLoop = new Loops(adjacencyMatrix);
        Paths testPaths = new Paths();
//        List<List<Integer>> loopPaths = testLoop.findAllDistinctLoopPaths(adjacencyMatrix);

//        if (loopPaths.isEmpty()) {
//            System.out.println("No distinct loop paths found in the graph.");
//        } else {
//            System.out.println("Distinct loop paths found in the graph:");
//            for (List<Integer> loopPath : loopPaths) {
//                System.out.println(loopPath);
//            }
//        }
        for(SingleLoop loop : testLoop.getLoopsInGraph()){
            System.out.println("Loop ID: " + loop.getLoopID());
            System.out.println("Loop Path: " + loop.getLoopPath());
            System.out.println("Loop Gain: " + loop.getLoopGain());
        }
        Paths paths = new Paths();
        paths.findForwardPaths(adjacencyMatrix);
        paths.findGainOfForwardPaths(adjacencyMatrix, paths.getForwardPaths());
        System.out.println("Forward paths from first node to last node:");
        for (List<Integer> path : paths.getForwardPaths()) {
            System.out.println(path);
            System.out.println("Gain: " + paths.getGainOfForwardPaths().get(paths.getForwardPaths().indexOf(path)));
        }
        System.out.println("Non touching :" +testLoop.getNonTouchingLoops());
        Deltas deltas = new Deltas(testLoop,paths.getForwardPaths(),adjacencyMatrix);
        System.out.println(deltas.getDelta());
        System.out.println(deltas.getDeltas());

        double[][] graphMatrix = {
//                {0, -10, 5, 0, 0},
//                {0, 0, 1, 25, 0},
//                {0, 0, 0, 6, 1},
//                {0, 0, 0, 0, 20},
//                {0, 0, 0, 0, 0}
                {0, 1, 0, 0, 0},
                {0, 0, 2, 0, 0},
                {5, 0, 0, 5, 0},
                {0, 0,3.7, 0, 5.3},
                {-3.5, 0, 0, 0, 0}
        };
//        Paths paths = new Paths();
//        paths.findForwardPaths(graphMatrix);
//        paths.findGainOfForwardPaths(graphMatrix, paths.getForwardPaths());
//        System.out.println("Forward paths from first node to last node:");
//        for (List<Integer> path : paths.getForwardPaths()) {
//            System.out.println(path);
//            System.out.println("Gain: " + paths.getGainOfForwardPaths().get(paths.getForwardPaths().indexOf(path)));
//        }

//        List<List<Integer>> forwardPaths = testPaths.findForwardPaths(adjacencyMatrix);
//        List<Integer> gainOfForwardPaths = testPaths.findGainOfForwardPaths(adjacencyMatrix, forwardPaths);
//            System.out.println("Forward paths from first node to last node:");
//            for (List<Integer> path : forwardPaths) {
//                System.out.println(path);
//                System.out.println("Gain: " + gainOfForwardPaths.get(forwardPaths.indexOf(path)));
//            }


        }
    }

