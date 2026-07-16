package org.example.irctc.controllers;


import lombok.RequiredArgsConstructor;
import org.example.irctc.dto.AuthResponse;
import org.example.irctc.dto.LoginRequest;
import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
import org.example.irctc.exception.UserFoundException;
import org.example.irctc.services.UserBookingServices;
import org.example.irctc.util.JwtUtil;
import org.example.irctc.util.UserServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.irctc.services.UserBookingServices;
import org.example.irctc.dto.SignupRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private final UserBookingServices userBookingServices ;

    @PostMapping("/signup")
    public ResponseEntity<String>userSignup(@RequestBody SignupRequest signupRequest){
        String name=signupRequest.getName();
        String email= signupRequest.getEmail();
        String password=signupRequest.getPassword();
        User newUser = new User(name, email, password, UserServiceUtil.hashPassword(password), new ArrayList<>());
        try {
            this.userBookingServices.signUp(newUser);
            return ResponseEntity.ok("User created successfully");
        } catch (UserFoundException e) {
            System.out.println("User cant be created !");
            return ResponseEntity.badRequest().body("User already exists");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>userLogin(@RequestBody LoginRequest loginRequest){
        String name=loginRequest.getName();
        String password=loginRequest.getPassword();
     //   User userToLogin = new User(name, password,UserServiceUtil.hashPassword(password));
 //       try {
//            userBookingServices = new UserBookingServices(userToLogin);
           // Optional<User> loggedInUser = userBookingServices.login();
            Optional<User> loggedInUser = userBookingServices.loginUser(name,password);
           // System.out.println(loggedInUser);
            if (loggedInUser.isPresent()) {
              //  userBookingServices = new UserBookingServices(loggedInUser.get());
                User user = loggedInUser.get();
                System.out.println(" Login successful! Welcome back.");
                String userIdStr = String.valueOf(user.getUserId());
                String accessToken = JwtUtil.generateAccessToken(userIdStr, user.getName());
                String refreshToken = JwtUtil.generateRefreshToken(userIdStr);
                return ResponseEntity.ok(new AuthResponse(accessToken,refreshToken,"Login successful!!"));
               } else {
                System.out.println(" Invalid username or password.");
                return ResponseEntity.badRequest().body(new AuthResponse(null, null, "Invalid username or password."));
               }

//        } catch (IOException e) {
//            System.out.println("Something error happedppedne in Login");
//        }
        //return ResponseEntity.internalServerError().body("Some internal error!!");
    }

    @GetMapping("/bookings")
    public ResponseEntity<?>getBookings(@RequestAttribute("userId") Long userId) {
       try {
           List<Ticket> userBookings = userBookingServices.fetchBookingsByUserId(userId);
        return ResponseEntity.ok(userBookings);
      } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving bookings: " + e.getMessage());
        }
    }
    //DELETE http://localhost:8080/api/users/bookings/{ticketId}
    @DeleteMapping("bookings/{ticketId}")
    public ResponseEntity<String> cancelUserBookings(@PathVariable String ticketId, @RequestAttribute("userId") String userId) {
        Long longTicketId = Long.parseLong(ticketId);
        Long longUserId = Long.parseLong(userId);
        Boolean isCancelled = userBookingServices.cancelBookingStateless(longTicketId,longUserId);
        if (isCancelled) {
            return ResponseEntity.ok("Booking cancelled successfully!");
        } else {
            return ResponseEntity.badRequest().body("Cancellation failed. Invalid Ticket ID or ownership mismatch.");
        }
    }

}
