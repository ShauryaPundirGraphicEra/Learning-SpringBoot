package org.example.irctc.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.irctc.dto.TrainRequest;
import org.example.irctc.entities.Seat;
import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
//import tools.jackson.core.type.TypeReference;
//import tools.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.irctc.repositories.SeatRepository;
import org.example.irctc.repositories.TicketRepository;
import org.example.irctc.repositories.TrainRepository;
import org.example.irctc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TrainService {
//    private Train train;
    @Autowired
   private final TrainRepository trainRepository;
    @Autowired
    private final SeatRepository seatRepository;
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final UserRepository userRepository;


//    public List<Train> searchTrains(String source,String destination){
//
//        return allTrains.stream()
//                .filter(train -> {
//                    List<String> stations = train.getStations();
//                    int indexOfSource = stations.indexOf(source);
//                    int indexOfDestination = stations.indexOf(destination);
//
//                    return indexOfSource != -1 && indexOfDestination != -1 && indexOfSource < indexOfDestination;
//                })
//                .collect(Collectors.toList());
//
//    }

    public List<Train> searchTrains(String source,String destination){
        List<Train> trains = trainRepository.findBySourceAndDestination(source,destination);
        return trains;
    }

    public Train searchTrainById(Long trainId){

        return trainRepository.findById(trainId)
                .orElse(null);
    }

    public Train addTrain(TrainRequest request) {
        Train newTrain = new Train();
        newTrain.setTrainName(request.getTrainName());
        newTrain.setTrainNo(request.getTrainNo());
        newTrain.setStations(request.getStations());
        newTrain.setStationTimes(request.getStationTimes());
        List<Seat> generatedSeats = new ArrayList<>();

        // 2. Loop to create the exact number of seats requested
        for (int i = 1; i <= request.getTotalSeats(); i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setBooked(false); // Default to available

            // Optional: Distribute seats into coaches (e.g., 60 seats per coach)
            // Coach 1: 1-60, Coach 2: 61-120, etc.
            int coachNumber = ((i - 1) / 60) + 1;
            seat.setCoach(coachNumber);

            // 3. IMPORTANT: Set the bi-directional relationship!
            // The seat must know which train it belongs to.
            seat.setTrain(newTrain);

            generatedSeats.add(seat);
        }

        // 4. Attach the populated list of seats to the train
        newTrain.setSeats(generatedSeats);
        return trainRepository.save(newTrain);
    }


}
