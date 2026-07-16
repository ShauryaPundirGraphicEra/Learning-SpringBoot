package org.example.irctc.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

//    public Optional<Ticket> bookSeat(String trainId, Integer seatNo,String userId){
//       if(userId==null){
//           System.out.println("Cannot fetch userId during booking !!!");
//           return Optional.empty();
//       }
//        Optional<Train> trainOptional=allTrains.stream().filter( train1 -> train1.getTrainId().equals(trainId)).findFirst();
//        if (trainOptional.isPresent()) {
//            Train train=trainOptional.get();
//            String destination=train.getStations().getLast();
//            String source=train.getStations().getFirst();
//
//            List<List<Integer>> seatsGrid = trainOptional.get().getSeats();;
//
//            if (seatsGrid == null || seatsGrid.isEmpty()) {
//                return Optional.empty();
//            }
//
//            int seatsPerRow = seatsGrid.get(0).size();
//
//            int rowIndex = (seatNo - 1) / seatsPerRow;
//            int colIndex = (seatNo - 1) % seatsPerRow;
//            if (rowIndex < 0 || rowIndex >= seatsGrid.size() || colIndex < 0 || colIndex >= seatsPerRow) {
//                return Optional.empty();
//            }
//
//            // 4. Check availability (0 = available, 1 = booked)
//            List<Integer> row = seatsGrid.get(rowIndex);
//            if (row.get(colIndex) == 0) {
//                row.set(colIndex, 1); // Mark as booked
//                String ticketId = UUID.randomUUID().toString();
//                Date dateOfTravel = new Date(); // Sets to current time/date of booking
//
//                Ticket ticket = new Ticket(ticketId, userId, source,destination, dateOfTravel, train);
//                return Optional.of(ticket);
//            }
//
//
//
//
//        }
//        return Optional.empty();
//    }


}
