package org.example.irctc.controllers;

import org.example.irctc.entities.Train;
import org.example.irctc.services.TrainService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {
    private final TrainService trainService;

    // Spring Boot will automatically run this constructor when the app starts
    public TrainController() throws IOException {
        this.trainService = new TrainService();
    }

    //  GET http://localhost:8080/api/trains/search?source=X&destination=Y
    @GetMapping("/search")
    public List<Train> searchTrains(@RequestParam String source, @RequestParam String destination) {

        return trainService.searchTrains(source, destination);
    }

}
