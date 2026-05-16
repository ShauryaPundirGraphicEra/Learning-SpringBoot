package org.example.irctc;

import org.example.irctc.entities.User;
import org.example.irctc.exception.UserFoundException;
import org.example.irctc.services.UserBookingServices;
import org.example.irctc.util.UserServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

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

		while(choice!=7){
			System.out.println("Choose option");
			System.out.println("1. Sign Up");
			System.out.println("2. Login");
			System.out.println("3. Fetch Bookings");
			System.out.println("4. Search Trains");
			System.out.println("5. Book a Seat/Ticket");
			System.out.println("6. Cancel Booking/Ticket");
			System.out.println("7. Exit App");
		}
		Scanner sc=new Scanner(System.in);
		choice=sc.nextInt();
		switch(choice){
			case 1:
				System.out.println("Enter username to sign up");
                String name=sc.next();
                System.out.println("Enter email to sign up");
                String email=sc.next();
                System.out.println("Enter password to sign up");
                String password=sc.next();
                User newUser=new User(name,email,password, UserServiceUtil.hashPassword(password),new ArrayList<>(), UUID.randomUUID().toString());
				try{
                    userBookingServices.signUp(newUser);
                }catch (UserFoundException e){
                    System.out.println("Similar User was found");
                }
                break;
			case 2:
				break;
		}




	}

}
