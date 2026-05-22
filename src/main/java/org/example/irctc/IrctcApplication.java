package org.example.irctc;

import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
import org.example.irctc.exception.UserFoundException;
import org.example.irctc.services.TrainService;
import org.example.irctc.services.UserBookingServices;
import org.example.irctc.util.UserServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class IrctcApplication {

	public static void main(String[] args) {

		//SpringApplication.run(IrctcApplication.class, args);
		System.out.println("Train Booking System is live!!!");

		int choice=0;
		UserBookingServices userBookingServices;

		try{
			userBookingServices=new UserBookingServices();
		} catch (IOException e) {
			System.out.println("Something is wrong !!");
			return;
		}
        List<Train> fetchedTrains = new ArrayList<>();
        String targetDestination = "";
        Optional<User>user;
		while(choice!=7) {
            System.out.println("Choose option");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat/Ticket");
            System.out.println("6. Cancel Booking/Ticket");
            System.out.println("7. Exit App");

            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter username to sign up");
                    String name = sc.next();
                    System.out.println("Enter email to sign up");
                    String email = sc.next();
                    System.out.println("Enter password to sign up");
                    String password = sc.next();
                    User newUser = new User(name, email, password, UserServiceUtil.hashPassword(password), new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingServices.signUp(newUser);
                    } catch (UserFoundException e) {
                        System.out.println("User cant be created !");
                    }
                    break;
                case 2:
                    System.out.println("Enter username to Login!");
                    String nameToLogin = sc.next();
                    System.out.println("Enter the password :");
                    String passwordToLogin = sc.next();
                    User userToLogin = new User(nameToLogin, passwordToLogin,
                            UserServiceUtil.hashPassword(passwordToLogin));
                    try {
                        userBookingServices = new UserBookingServices(userToLogin);
                        Optional<User> loggedInUser = userBookingServices.login();
                        if (loggedInUser.isPresent()) {
                            userBookingServices = new UserBookingServices(loggedInUser.get());
                            System.out.println(" Login successful! Welcome back.");
                        } else {
                            System.out.println(" Invalid username or password.");
                        }
                    } catch (IOException e) {
                        System.out.println("Something error happedppedne in Login");
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your Booking");
                    userBookingServices.fetchBooking();
                    break;
                case 4:
                    System.out.println("Enter ur start / source station");
                    String source = sc.next();
                    System.out.println("Enter ur destination station");
                    String destination = sc.next();
                    fetchedTrains = userBookingServices.getTrains(source, destination);
                    targetDestination=destination;
                    if (fetchedTrains.isEmpty()) {
                        System.out.println("No trains found matching this route.");
                    } else {
                        System.out.println("\nAvailable Trains:");
                        //System.out.println(fetchedTrains);
                        for (int i = 0; i < fetchedTrains.size(); i++) {
                            System.out.printf("[%d] %s",
                                    (i + 1),
                                    fetchedTrains.get(i).getTrainInfo());
                        }
                    }
                    break;
                case 5:

                    if (fetchedTrains.isEmpty()) {
                        System.out.println("Please search for trains first using Option 4!");
                        break;
                    }

                    System.out.printf("Select train number (1 to %d): ", fetchedTrains.size());
                    int trainIndex = sc.nextInt() - 1; // Convert back to 0-based list indexing

                    if (trainIndex < 0 || trainIndex >= fetchedTrains.size()) {
                        System.out.println("Invalid selection selection index!");
                        break;
                    }

                    Train selectedTrain = fetchedTrains.get(trainIndex);

                    System.out.print("Enter the seat number you want to book: ");
                    int seatNo = sc.nextInt();

                    // Call the booking logic. It handles the UUID trainId internally.
                    Optional<Ticket> ticketOpt = Optional.empty();
                    try {
                        ticketOpt = userBookingServices.bookSeat(selectedTrain.getTrainId(), seatNo, targetDestination);
                    } catch (IOException e) {
                        System.out.println("Internal server error due to IO ops");
                    }

                    if (ticketOpt.isPresent()) {
                        System.out.println("Booking Successful!");
                        System.out.println(ticketOpt.get().getTicketInfo());
                    } else {
                        System.out.println(" Booking failed. Seat might be already taken or invalid.");
                    }
                    break;

                case 6:
                    System.out.print("Enter the Ticket ID you want to cancel: ");
                    String ticketIdToCancel = sc.next();


                        Boolean isCancelled = userBookingServices.cancelBooking(ticketIdToCancel);

                        if (isCancelled) {
                            System.out.println("Booking cancelled successfully!");
                        } else {
                            System.out.println("Cancel failed! Invalid Ticket ID or no tickets found.");
                        }

                    break;

                case 7:
                    System.out.println("Exiting application. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");

            }


        }

	}

}
