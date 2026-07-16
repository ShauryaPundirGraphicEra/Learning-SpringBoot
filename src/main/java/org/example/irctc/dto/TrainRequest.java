package org.example.irctc.dto;

import java.util.List;
import java.util.Map;

public class TrainRequest {
    private String trainName;
    private String trainNo;
    private List<String> stations;
    private Map<String, String> stationTimes;
    private int totalSeats;

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    // Getters and Setters (or use @Data if you have Lombok enabled)
    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public List<String> getStations() { return stations; }
    public void setStations(List<String> stations) { this.stations = stations; }

    public Map<String, String> getStationTimes() { return stationTimes; }
    public void setStationTimes(Map<String, String> stationTimes) { this.stationTimes = stationTimes; }
}