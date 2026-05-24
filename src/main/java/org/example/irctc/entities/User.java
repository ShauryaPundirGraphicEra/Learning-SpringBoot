package org.example.irctc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import java.util.List;


public class User {
    @Getter @Setter
    @JsonProperty("name")
    private String name;
    @Getter @Setter
    @JsonProperty("email")
    private String email;
    @Getter @Setter
    @JsonProperty("password")
    private String password;
    @Getter @Setter
    @JsonProperty("hashedPassword")
    private String hashedPassword;
    @Getter @Setter
    @JsonProperty("ticketBooked")
    private List<Ticket>ticketBooked;
    @Getter @Setter
    @JsonProperty("userId")
    private String userId;


    public User(@JsonProperty("name")String name,@JsonProperty("email")String email,@JsonProperty("password")String password,@JsonProperty("hashedPassword")String hashedPassword,@JsonProperty("ticketBooked")List<Ticket>ticketBooked,@JsonProperty("userId")String userId){
        this.name=name;
        this.userId=userId;
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

    public User(){}

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
