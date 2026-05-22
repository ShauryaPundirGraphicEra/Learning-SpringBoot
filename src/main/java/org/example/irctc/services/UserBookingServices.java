package org.example.irctc.services;
import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.util.UserServiceUtil;
import org.example.irctc.exception.UserFoundException;

import org.example.irctc.entities.User;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// object mapper is used to convert the json-> Object (User) -----> Deserialize
// Object(User)-> json ------>Serialize





public class UserBookingServices {
    private User user;
    private static final String USER_PATH="C:\\Users\\ACER\\OneDrive\\Desktop\\SpringBoot\\IRCTC\\src\\main\\java\\org\\example\\irctc\\localDB\\users.json";
    private ObjectMapper objectMapper=new ObjectMapper();
    public List<User>userList;

    //constructor
    public UserBookingServices(User user1) throws IOException {
        this.user=user1;
        this.userList=loadUser();
    }



    public UserBookingServices()throws IOException {
       userList=loadUser();
    }

    public List<User> loadUser() throws IOException{
        File users=new File(USER_PATH);
        if(!users.exists() || users.length()==0){
            return new ArrayList<>();
        }
       return objectMapper.readValue(users, new TypeReference<List<User>>() {});  //to perfrom object mapping ,we do deserialization here
    }

    public Boolean findUser(User user1){
      return userList.parallelStream().anyMatch( user->user!=null && user.getEmail() != null && user.getEmail().equalsIgnoreCase((user1.getEmail())));

    }

    public Optional<User> login(){
        Optional<User> fetchedUser=userList.parallelStream().filter(user1->{
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword()); }).findFirst();

       if(fetchedUser.isPresent()){
           this.user=fetchedUser.get();
           return fetchedUser;
       }else{
           return Optional.empty();
       }
    }

    public Boolean signUp(User user1) throws UserFoundException{
        if(findUser(user1)){
            throw new UserFoundException("User found with same credentials");
        }
        user1.setUserId(UUID.randomUUID().toString());
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException e){
            return Boolean.FALSE;
        }


    }

    private void saveUserListToFile()throws IOException{
        File userFile=new File(USER_PATH);

        objectMapper.writeValue(userFile,userList);
    }

    public void fetchBooking(){

        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){
     if(user.getTicketBooked()==null){
         return Boolean.FALSE;
     }
     long initialSize=user.getTicketBooked().size();

      //fetch tickets of the current user-> remove the ticketId provided by the user from the user's ticketBooked list->update in the DB

      user.setTicketBooked(user.getTicketBooked().parallelStream().
              filter(ticket->!ticket.getTicketId().equals(ticketId))
              .collect(Collectors.toList())
      );

      if(user.getTicketBooked().size()==initialSize){
          return Boolean.FALSE;
      }
      try{
          for (int i = 0; i < userList.size(); i++) {
              if (userList.get(i).getEmail().equalsIgnoreCase(user.getEmail())) {
                  userList.set(i, user);
                  break;
              }
          }
          // Commit changes to  JSON file
          saveUserListToFile();
          return Boolean.TRUE;
      }catch (IOException e){
          return Boolean.FALSE;
      }
    }

    public List<Train> getTrains(String source,String destination){
       List<Train>ans=null;
        try{
           TrainService t=new TrainService();
           ans= t.searchTrains(source,destination);
        }catch(IOException e){
            System.out.println("No train available!!");
        }
        return ans;
    }


    public Optional<Ticket> bookSeat(String trainId, Integer seatNo, String destination) throws IOException {

        if (this.user == null) {
            System.out.println("Error: You must be logged in to book a ticket.");
            return Optional.empty();
        }


        String loggedInUserId = this.user.getUserId();
        TrainService t=new TrainService();

        Optional<Ticket>ticketOpt= t.bookSeat(trainId, seatNo, loggedInUserId, destination);
        if(ticketOpt.isPresent()){
            if (user.getTicketBooked() == null) {
                user.setTicketBooked(new ArrayList<>());
            }
            user.getTicketBooked().add(ticketOpt.get());
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserId() != null && userList.get(i).getUserId().equals(loggedInUserId)) {
                    userList.set(i, user);
                    break;
                }
            }
            saveUserListToFile();
        }
        return ticketOpt;
    }



}
