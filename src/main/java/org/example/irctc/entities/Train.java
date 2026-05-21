package org.example.irctc.entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Train {
    @Getter @Setter
    @JsonProperty("train_id")
    private String trainId;

    @Getter @Setter
    @JsonProperty("train_no")
    private String trainNo;

    @Getter @Setter
    private List<List<Integer>>seats;
    @Getter @Setter
    @JsonProperty("station_times")
    private Map<String,String>stationTimes;
    @Getter @Setter
    private List<String>stations;

    public Train( @JsonProperty("train_id") String trainId,
                  @JsonProperty("train_no") String trainNo,
                  List<List<Integer>> seats,
                  @JsonProperty("station_times") Map<String, String> stationTimes,
                  List<String> stations){
        this.seats=seats;
        this.trainNo=trainNo;
        this.stationTimes=stationTimes;
        this.trainId=trainId;
        this.stations=stations;
    }

//    public String getTrainInfo(){
//        return String.format("Train ID: %s -- Train No: %s",trainId,trainNo);
//    }
public String getTrainInfo() {
    // 1. Calculate occupied vs total seats from the 2D list
    int totalSeats = 0;
    int occupiedSeats = 0;

    if (seats != null) {
        for (List<Integer> row : seats) {
            for (Integer seat : row) {
                totalSeats++;
                if (seat == 1) { // 1 means booked/occupied
                    occupiedSeats++;
                }
            }
        }
    }
    int availableSeats = totalSeats - occupiedSeats;

    // 2. Format the intermediate stations route path (e.g., StationA -> StationB -> StationC)
    String routePath = "No route defined";
    if (stations != null && !stations.isEmpty()) {
        routePath = String.join(" ➔ ", stations);
    }

    // 3. Construct a clear, visually structured dashboard string
    return String.format(
            "==================================================\n" +
                    " TRAIN: %s (ID: %s)\n" +
                    "️  ROUTE: %s\n" +
                    " SEATS: %d Available / %d Total (%d Occupied)\n" +
                    "==================================================",
            trainNo, trainId, routePath, availableSeats, totalSeats, occupiedSeats
    );
}


}
