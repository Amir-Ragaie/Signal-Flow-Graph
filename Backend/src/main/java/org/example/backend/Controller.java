package org.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class Controller {
//    @Autowired
    @PostMapping("/solver")
    public Service solvingSFG(@RequestBody double[][]graph){
        System.out.println(graph);
        Service service = new Service(graph);
        return service;
    }
}
