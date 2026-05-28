package org.example.irctc.controllers;

import org.example.irctc.dto.BookingRequest;
import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.services.TrainService;
import org.example.irctc.services.UserBookingServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/bookings/{trainId}")  //trainId
    public ResponseEntity<?> bookUserSeat(@PathVariable String trainId, @RequestBody BookingRequest bookingRequest, @RequestAttribute("userId") String userId){
        // Train selectedTrain = fetchedTrains.get(seatNo); To Complete
        Train fetchedTrain=trainService.searchTrainById(trainId);
        if (fetchedTrain == null) {

            return ResponseEntity.status(404).body("Error finding the train");
        }
        //to complete
        try{
            UserBookingServices userBookingServices = new UserBookingServices();


            Optional<Ticket> ticketOpt = userBookingServices.bookSeatStateless(
                    trainId, bookingRequest.getSeatNo(), userId);
            if (ticketOpt.isPresent()) {

                return ResponseEntity.ok(ticketOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Booking failed. The selected seat may already be occupied or invalid.");
            }
        } catch (IOException e) {
            System.out.println("System Exception during database file modification operations: " + e.getMessage());
            return ResponseEntity.internalServerError().body("An internal server error occurred while writing data.");
        }

    }


}
