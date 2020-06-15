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
		
		System.out.println("\nLOGIN");
	
		while(userType == 0 && loginAttempts != 0) {
			System.out.println("You have " + loginAttempts + " more login attempts left");
			System.out.println();
			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Password: ");
			int password = scanner.nextInt();
			scanner.nextLine();
			
			userType = loginController_.login(email, password);
			if( userType == 0) {
				System.out.println("\nEmail or password are incorrect.\nPlease try again.\n");
				loginAttempts--;
			}
		}
		//end while
		
		String msg;
		
		if(userType != 0) {
			
			msg = "Hello " + usersController_.getUserName() + "!";
		}
		else
			msg = "No more login attempts, exit the program!";
		
		System.out.println();
		System.out.println(msg);
		
		return userType;
	}
}
