package view;

import java.io.IOException;
import java.util.Scanner;

import controller.LoginController;
import controller.UsersController;

public class Login {
	
	private LoginController loginController_;
	private UsersController usersController_;
	private int loginAttempts = 3;
	public Login() {
		loginController_ = new LoginController();
		usersController_ = new UsersController();
	}
	
	@SuppressWarnings("resource")
	public int signIn() throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		int userType = 0;
		
		System.out.println("-------------------------------------------Login--------------------------------------------\n");

		while(userType == 0 && loginAttempts != 0) {
			
			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Password: ");
			int password = scanner.nextInt();
			scanner.nextLine();
			
			userType = loginController_.login(email, password);
			if(userType == 0) {
				if(loginAttempts != 1) {//if not last login attempt
					System.out.println(); //enter
					System.out.println("! Email or password are incorrect. Please try again.");
				}
				loginAttempts--;
				if(loginAttempts != 0) {
					System.out.println("You have " + loginAttempts + " more login attempts left.");
					System.out.println();
				}
					

			}
		}
		//end while
		
		String msg;
		if(userType != 0) {
			
			msg = "Hello " + usersController_.getUserName() + "! You are now logged in as ";
			switch(userType){
				case 1: 
					msg += "Admin";
				break;
				case 2: 
					msg += "Seller";
				break;
				case 3: 
					msg += "Supplier";
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
