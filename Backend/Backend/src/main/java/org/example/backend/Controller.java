package org.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
//    @Autowired
    @PostMapping("/solver")
    public Service solvingSFG(@RequestBody String str){
        System.out.println(str);
        str = str.substring(1, str.length() - 1);
        String[] subarrays = str.split("\\],\\[");

        List<double[]> list = new ArrayList<>();

        for (String subarray : subarrays) {
            subarray = subarray.replaceAll("\\[|\\]", "");
            String[] elements = subarray.split(",");
            double[] doubles = new double[elements.length];
            for (int i = 0; i < elements.length; i++) {
                String element = elements[i].replaceAll("^\"|\"$", "");
                try {
                    doubles[i] = Double.parseDouble(element);
                } catch (NumberFormatException e) {
                    // Handle non-numeric values (e.g., strings)
                    // Set to NaN or any other default value as needed
                    doubles[i] = Double.NaN;
                }
            }
            list.add(doubles);
        }

        // Convert list to double[][] array
        double[][] graph = new double[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            graph[i] = list.get(i);
//            for(double a:graph[i]) System.out.print(a + " ");
//            System.out.println("");
        }
        for (double[] row : graph) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        Paths paths = new Paths();
        paths.findForwardPaths(graph);
        paths.findGainOfForwardPaths(graph,paths.getForwardPaths());
        Loops loops = new Loops(graph);
        Deltas deltas = new Deltas(loops,paths.getForwardPaths(),graph);
        List<List<Integer>> loopsInGraph = new ArrayList<>();
        for(SingleLoop l:loops.getLoopsInGraph()){
            loopsInGraph.add(l.getLoopPath());
        }
        Service service = new Service(paths.getForwardPaths(),paths.getGainOfForwardPaths(),loopsInGraph, deltas.getDelta(),deltas.getDeltas(), loops.getNonTouchingLoops());
        return service;
    }
}
