package org.example.irctc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="ticket")
@Getter
@Setter

public class Ticket {
    @Column(name = "ticket_id")
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private String userId;

    private String source;

    private String destination;

    @Column(name = "date_of_travel")
    private Date dateOfTravel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

     public Ticket(String ticketId, String userId,String source, String destination, Date dateOfTravel, Train train){
         this.ticketId=ticketId;
         this.userId=userId;
         this.source=source;
         this.destination=destination;
         this.dateOfTravel=dateOfTravel;
         this.train=train;
     }
    public Ticket(){}

    @JsonIgnore
    public String getTicketInfo(){

        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, train.getStations().get(0), destination, dateOfTravel);
    }

}
