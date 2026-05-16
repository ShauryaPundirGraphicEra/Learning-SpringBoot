package org.example.irctc.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Ticket {
    @Getter @Setter
    private String ticketId;
    @Getter @Setter
    private String userId;
    @Getter @Setter
    private String destination;
    @Getter @Setter
    private Date dateOfTravel;
    @Getter @Setter
    private Train train;

     public Ticket(String ticketId, String userId, String destination, Date dateOfTravel, Train train){
         this.ticketId=ticketId;
         this.userId=userId;
         this.destination=destination;
         this.dateOfTravel=dateOfTravel;
         this.train=train;
     }
    public Ticket(){}

    public String getTicketInfo(){

        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, train.getStations().get(0), destination, dateOfTravel);
    }

}
