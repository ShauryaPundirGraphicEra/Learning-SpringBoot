package org.example.irctc.services;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.irctc.entities.Seat;
import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.repositories.SeatRepository;
import org.example.irctc.repositories.TicketRepository;
import org.example.irctc.repositories.TrainRepository;
import org.example.irctc.util.UserServiceUtil;
import org.example.irctc.exception.UserFoundException;

import org.example.irctc.entities.User;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.example.irctc.repositories.UserRepository;
import org.springframework.stereotype.Service;





@Service
@RequiredArgsConstructor
public class UserBookingServices {
    private User user;
    private List<Train>allTrains;

    private final UserRepository userRepository;
    private final TrainRepository trainRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;






//    public Boolean findUser(User user1){
//      return userList.parallelStream().anyMatch( user->user!=null && user.getEmail() != null && user.getEmail().equalsIgnoreCase((user1.getEmail())));
//
//    }

    public User findUserById(long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }




//    @Transactional(readOnly = true) //Safe, read-only transaction for lazy fetch
    public void printUserTickets(Long userId) {
        // Fetch the user from MySQL database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> tickets = user.getTicketBooked();


        if (tickets == null || tickets.isEmpty()) {
            System.out.println("No tickets/booking yet for user: " + user.getName());
            return;
        }

        System.out.println("Booking history for " + user.getName() + ":");
        for (Ticket ticket : tickets) {
           // System.out.println(ticket.getTicketInfo());
            System.out.println(ticket);
        }
    }


    public Optional<User> loginUser(String name, String rawPassword) {
        Optional<User> user = userRepository.findByName(name);

        if(user.isPresent()
                && UserServiceUtil.checkPassword(rawPassword,
                user.get().getHashedPassword())){
            return user;
        }

        return Optional.empty();
    }



    public Boolean signUp(User user) throws UserFoundException{
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserFoundException("User already exists");

        }

        userRepository.save(user);

        return true;
    }


    public List<Ticket> fetchBookingsByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        return ticketRepository.findByUserUserId(userId);
    }
    @Transactional
    public Boolean cancelBookingStateless(Long ticketId,Long userId){
        Optional<Ticket> ticketOpt =
                ticketRepository.findByTicketIdAndUserUserId(ticketId, userId);

        if (ticketOpt.isEmpty()) {
            return false;
        }

        ticketRepository.delete(ticketOpt.get());

        return true;

    }


    public List<Train> getTrains(String source,String destination){
        List<Train> trains = trainRepository.findBySourceAndDestination(source,destination);
        return trains;
    }




    // Fully stateless coordination between TrainService and User data updates
    @Transactional
    public Optional<Ticket> bookSeatStateless(Long trainId, Integer seatNo,Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        Seat seat = seatRepository
                .findByTrainTrainIdAndSeatNumber(trainId, seatNo)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.isBooked()) {
            return Optional.empty();
        }

        seat.setBooked(true);
        seatRepository.save(seat);

        Ticket ticket = new Ticket();

        ticket.setUser(user);
        ticket.setTrain(train);

        ticket.setSource(train.getStations().getFirst());

        ticket.setDestination(
                train.getStations().getLast());

        ticket.setDateOfTravel(new Date());

        Ticket savedTicket = ticketRepository.save(ticket);

        return Optional.of(savedTicket);
    }


}
