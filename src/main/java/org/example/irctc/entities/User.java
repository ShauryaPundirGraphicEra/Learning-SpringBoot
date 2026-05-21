package org.example.irctc.entities;

import lombok.Getter;
import lombok.Setter;


import java.util.List;


public class User {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String hashedPassword;
    @Getter @Setter
    private List<Ticket>ticketBooked;
    @Getter @Setter
    private String userId;


    public User(String name,String email,String password,String hashedPassword,List<Ticket>ticketBooked,String userId){
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
