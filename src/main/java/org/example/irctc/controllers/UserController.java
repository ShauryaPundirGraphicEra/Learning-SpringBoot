package org.example.irctc.controllers;


import org.example.irctc.dto.LoginRequest;
import org.example.irctc.entities.User;
import org.example.irctc.exception.UserFoundException;
import org.example.irctc.services.UserBookingServices;
import org.example.irctc.util.UserServiceUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.irctc.services.UserBookingServices;
import org.example.irctc.dto.SignupRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("api/users")
public class UserController {
    private final  UserBookingServices userBookingServices ;
    public UserController(){
        try {
            this.userBookingServices=new UserBookingServices();
        } catch (IOException e) {
            System.out.println("Failed to initialize user booking services");
            throw new RuntimeException("Fatal: Failed to initialize user booking services", e);
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<String>userSignup(@RequestBody SignupRequest signupRequest){
        String name=signupRequest.getName();
        String email= signupRequest.getEmail();
        String password=signupRequest.getPassword();
        User newUser = new User(name, email, password, UserServiceUtil.hashPassword(password), new ArrayList<>(), UUID.randomUUID().toString());
        try {
            this.userBookingServices.signUp(newUser);
            return ResponseEntity.ok("User created successfully");
        } catch (UserFoundException e) {
            System.out.println("User cant be created !");
            return ResponseEntity.badRequest().body("User already exists");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String>userLogin(@RequestBody LoginRequest loginRequest){
        String name=loginRequest.getName();
        String password=loginRequest.getPassword();
        User userToLogin = new User(name, password,UserServiceUtil.hashPassword(password));
 //       try {
//            userBookingServices = new UserBookingServices(userToLogin);
           // Optional<User> loggedInUser = userBookingServices.login();
            Optional<User> loggedInUser = userBookingServices.loginUser(name,password);
           // System.out.println(loggedInUser);
            if (loggedInUser.isPresent()) {
              //  userBookingServices = new UserBookingServices(loggedInUser.get());
                System.out.println(" Login successful! Welcome back.");
                return ResponseEntity.ok("Login successful!!");
               } else {
                System.out.println(" Invalid username or password.");
                return ResponseEntity.badRequest().body("Invalid username or password.");
               }

//        } catch (IOException e) {
//            System.out.println("Something error happedppedne in Login");
//        }
        //return ResponseEntity.internalServerError().body("Some internal error!!");
    }

}
