package org.example.irctc.entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Train {
    @Getter @Setter
    private String trainId;
    @Getter @Setter
    private String trainNo;
    @Getter @Setter
    private List<List<Integer>>seats;
    @Getter @Setter
    private Map<String,String>stationTimes;
    @Getter @Setter
    private List<String>stations;

    public Train(String trainId,String trainNo,List<List<Integer>>seats,Map<String,String>stationTimes,List<String>stations){
        this.seats=seats;
        this.trainNo=trainNo;
        this.stationTimes=stationTimes;
        this.trainId=trainId;
        this.stations=stations;
    }

    public String getTrainInfo(){
        return String.format("Train ID: %s -- Train No: %s",trainId,trainNo);
    }

}
