package org.example.irctc.services;

import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
//import tools.jackson.core.type.TypeReference;
//import tools.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;



public class TrainService {
//    private Train train;
    private static final String USER_PATH="C:\\Users\\ACER\\OneDrive\\Desktop\\SpringBoot\\IRCTC\\src\\main\\java\\org\\example\\irctc\\localDB\\trains.json";
    private ObjectMapper objectMapper=new ObjectMapper();
    private List<Train>allTrains;

    public TrainService() throws IOException{
        allTrains=loadTrain();
    }

    public List<Train> loadTrain() throws IOException {
        File trains=new File(USER_PATH);
        return objectMapper.readValue(trains, new TypeReference<List<Train>>() {});  //to perfrom object mapping ,we do deserialization here
    }

    public List<Train> searchTrains(String source,String destination){

        return allTrains.stream()
                .filter(train -> {
                    List<String> stations = train.getStations();
                    int indexOfSource = stations.indexOf(source);
                    int indexOfDestination = stations.indexOf(destination);

                    return indexOfSource != -1 && indexOfDestination != -1 && indexOfSource < indexOfDestination;
                })
                .collect(Collectors.toList());

    }

    public Optional<Ticket> bookSeat(String trainId, Integer seatNo,String userId,String destination){
       if(userId==null){
           System.out.println("Cannot fetch userId during booking !!!");
           return Optional.empty();
       }
        Optional<Train> trainOptional=allTrains.stream().filter( train1 -> train1.getTrainId().equals(trainId)).findFirst();
        if (trainOptional.isPresent()) {
            Train train=trainOptional.get();

            List<List<Integer>> seatsGrid = trainOptional.get().getSeats();;

            if (seatsGrid == null || seatsGrid.isEmpty()) {
                return Optional.empty();
            }

            // 2. Calculate rows and columns based on the grid structure
            int seatsPerRow = seatsGrid.get(0).size();

            // Convert flat 1-based seat number to 0-based 2D matrix indices
            int rowIndex = (seatNo - 1) / seatsPerRow;
            int colIndex = (seatNo - 1) % seatsPerRow;

            // 3. Boundary check to avoid IndexOutOfBoundsException
            if (rowIndex < 0 || rowIndex >= seatsGrid.size() || colIndex < 0 || colIndex >= seatsPerRow) {
                return Optional.empty();
            }

            // 4. Check availability (0 = available, 1 = booked)
            List<Integer> row = seatsGrid.get(rowIndex);
            if (row.get(colIndex) == 0) {
                row.set(colIndex, 1); // Mark as booked
                String ticketId = UUID.randomUUID().toString();
                Date dateOfTravel = new Date(); // Sets to current time/date of booking

                Ticket ticket = new Ticket(ticketId, userId, destination, dateOfTravel, train);
                return Optional.of(ticket);
            }




        }
        return Optional.empty();
    }


}
