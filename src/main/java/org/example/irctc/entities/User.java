package org.example.irctc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity            // Tells Hibernate to create a MySQL table named "users"
@Table(name = "users")
public class User {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;


    @JsonProperty("password")
    private String password;

    @Column(name = "hashed_password")
    @JsonProperty("hashedPassword")
    private String hashedPassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty("ticketBooked")
    private List<Ticket> ticketBooked=new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    @JsonProperty("userId")
    private long userId;


    public User(@JsonProperty("name")String name,@JsonProperty("email")String email,@JsonProperty("password")String password,@JsonProperty("hashedPassword")String hashedPassword,@JsonProperty("ticketBooked")List<Ticket>ticketBooked){
        this.name=name;
        this.email=email;
        this.ticketBooked=ticketBooked;
        this.password=password;
        this.hashedPassword=hashedPassword;
    }
    //for login
    public User(String userName,String password,String hashedPassword){
        this.name=userName;
        this.password=password;
        this.hashedPassword=hashedPassword;
    }

//    public User(String userName,String password){
//        this.name=;
//        this.password=password;
//
//    }


    public void printTickets(){
        if(ticketBooked==null || ticketBooked.isEmpty()){
            System.out.println("No tickets/booking yet");
        }else {
            for (int i = 0; i < ticketBooked.size(); i++) {
                System.out.println(ticketBooked.get(i).getTicketInfo());

            }
        }
    }






}
