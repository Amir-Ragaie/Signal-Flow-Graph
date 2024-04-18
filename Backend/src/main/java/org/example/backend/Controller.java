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
    public String solvingSFG(@RequestBody String str){
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
        }
        for (double[] row : graph) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        Service service = new Service(graph);
        String result = "The forward paths are: " + service.paths.getForwardPaths().toString() + "\n" +
                "The loops are: " + service.loops.getLoopsInGraph().toString() + "\n" +
                "The 2 Non-touching loops are "+ service.loops.getNonTouchingLoops().toString()+"\n"+
                "The delta is: " + Double.toString(service.deltas.getDelta()) + "\n" +
                "The deltas are: " + service.deltas.getDeltas().toString();
        System.out.println(result);
        return result;
    }
}
