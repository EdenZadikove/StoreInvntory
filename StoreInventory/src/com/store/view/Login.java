package com.store.view;

import java.io.IOException;
import java.util.Scanner;

import com.store.controller.LoginController;
import com.store.controller.UsersController;

public class Login {
	
	private ViewFunctions viewFunctions_;
	private LoginController loginController_;
	private UsersController usersController_;
	private int loginAttempts_;
	private Scanner scanner_;

	public Login() {
		viewFunctions_ = ViewFunctions.getInstance();
		loginController_ = new LoginController();
		usersController_ = new UsersController();
		loginAttempts_ = 3;
		scanner_ = new Scanner(System.in);
	}
	
	public int signIn() throws IOException {
		int userType = 0;
		System.out.println(viewFunctions_.getLoginHeader() + "\n");
		while(userType == 0 && loginAttempts_ != 0) {	
			System.out.print("Email: ");
			String email = scanner_.nextLine();
			System.out.print("Password: ");
			
			int password = scanner_.nextInt();
			scanner_.nextLine();
			
			userType = loginController_.login(email, password); //if userType == 0 then userName or password are incorrect
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
			msg = "Hello " + usersController_.getUserName() + "! You are now logged in as ";
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
		System.out.println(); //enter
		System.out.println(msg);		
		return userType;
	}
}