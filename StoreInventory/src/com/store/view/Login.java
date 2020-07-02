package com.store.view;

import java.util.Scanner;

import com.store.controller.UserSessionServiceController;

public class Login {
	
	private ViewFunctions viewFunctions_;
	private UserSessionServiceController userSessionServiceController_;
	private int loginAttempts_;
	private Scanner scanner_;

	public Login(){
		viewFunctions_ = ViewFunctions.getInstance();
		userSessionServiceController_ = new UserSessionServiceController();
		loginAttempts_ = 3;
		scanner_ = new Scanner(System.in);
	}
	
	public int signIn(){
		int userType = 0; //if userType == 0, then login attempt failed
		int password = -1; //initialize password to negative value (password can be only positive numbers)
		String email = "";
		System.out.println(viewFunctions_.getLoginHeader() + "\n"); //print login header
		
		while(userType == 0 && loginAttempts_ != 0) {	//while user have login attempts
			System.out.print("Email: ");
			email = scanner_.nextLine();
			password = viewFunctions_.validateIntInput("Password: "); //catch exception in scannerInt
			
			try{
				userType = userSessionServiceController_.login(email, password); //if userType == 0 then userName or password are incorrect
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			
			if(userType == 0) {
				if(loginAttempts_ != 1) //if not last login attempt
					System.out.println("\n! Email or password are incorrect. Please try again.");
				loginAttempts_--; 
				if(loginAttempts_ != 0) //if login attempts are not over
					System.out.println("You have " + loginAttempts_ + " more login attempts left.\n");
			}
		}
		//end while
		
		String msg = "";
		if(userType != 0) {
			msg = "Hello " + userSessionServiceController_.getUserName() + "! You are now logged in as ";
			switch(userType){
				case 1: 
					msg += "an admin.";
				break;
				case 2: 
					msg += "a seller.";
				break;
				case 3: 
					msg += "a supplier.";
				break;
			}
		}
		else
			msg = "! No more login attempts.";
		System.out.println("\n" + viewFunctions_.getSeperator() + "\n\n" + msg);
		return userType; //go back to welcome page
	}
}