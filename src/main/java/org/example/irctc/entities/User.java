package org.example.irctc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity            // Tells Hibernate to create a MySQL table named "users"
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "user_id")
    private String userId;

    private String name;

    private String email;

    private String password;

    //@Column(name = "hashed_password")
    private String hashedPassword;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketBooked=new ArrayList<>();


//
//
//    public User(@JsonProperty("name")String name,@JsonProperty("email")String email,@JsonProperty("password")String password,@JsonProperty("hashedPassword")String hashedPassword,@JsonProperty("ticketBooked")List<Ticket>ticketBooked){
//        this.name=name;
//        this.email=email;
//        this.ticketBooked=ticketBooked;
//        this.password=password;
//        this.hashedPassword=hashedPassword;
//    }
//    //for login
//    public User(String userName,String password,String hashedPassword){
//        this.name=userName;
//        this.password=password;
//        this.hashedPassword=hashedPassword;
//    }
//
////    public User(String userName,String password){
////        this.name=;
////        this.password=password;
////
////    }


}
