package com.store.view;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.store.controller.UsersController;

public class Users{
	
	private UsersController usersController_;
	private ViewFunctions viewFunctions_;
	private String prevScreens_;
	private Scanner scanner_;
	
	public Users() {
		usersController_ = new UsersController();
		viewFunctions_ = new ViewFunctions();
		prevScreens_ = viewFunctions_.getPrevScreens() + " -----> " + "Users Manager Menu";
		scanner_ = new Scanner(System.in);
	}
	
	public int showMenu() {
		int command = -1; 
		int validCommandFlag = 0;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		boolean showStoreManagerMenu_again = false; //true- show again, false- don't show again
		
		System.out.println(viewFunctions_.getStoreMenuHeader());
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions_.getSeperator());	
			showUsersManagerMenu();
			firstTimeMenuFlag = false; //not the first time anymore
			
			command = viewFunctions_.validateIntInput("I want to: ");
			command = viewFunctions_.validateInsertedData(1,3,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showStoreManagerMenu_again = actionNavigate(command); //command choice from Store Manager Menu
			
			if(!showStoreManagerMenu_again) {  //showStoreManagerMenu_again == false
				if(command == -1)
					System.out.println("Going back to Main Menu...");
				else { 
					command = 0; //for logout
					actionNavigate(0); //exit the program
				}
				validCommandFlag = 1; //end loop
			}
		}
		//end while
		return command;
	}
	
	
	private boolean actionNavigate(int command) {
		boolean result = false; //if result == true then show menu again
		switch(command) {
		case 1: //View all users
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "View Existing users"));
			showUsersTable(); 
			result = true;
			break;
		case 2: //Create a new user 
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "Create a new user"));
			createNewUser();
			result = viewFunctions_.anotherActions();
			break;
		case 3: //Create a new user 
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "Delete existing user"));
			deleteUser();
			result = viewFunctions_.anotherActions();
			break;
		default:
			usersController_.saveToFileUsers();
			break;
		}
		return result;
	}


	private void showUsersTable() {
		ArrayList<String> usersList = usersController_.getAllUsers();
		
		System.out.println("Users Table:\n");
		System.out.printf("| %-20s| %-20s| %-20s | %-20s |\n","First Name", "Last Name", "Email", "Role");
		for(String user : usersList) {
			String userName = StringUtils.substringBetween(user, "userName:", ";");
			String firstName = splitString(userName, "_", 0);
			String lastName = splitString(userName, "_", 1);
			String email = StringUtils.substringBetween(user, "email:", ";");
			String userType = StringUtils.substringBetween(user, "userType:", ";");

			switch(userType) {
			case "1":
				userType = "Admin"; 
				break;
			case "2":
				userType = "Seller";
				break;
			case "3":
				userType = "Supplier";
				break;
			default:
				userType = "Not Valid";
				break;
			}
			System.out.printf("| %-20s| %-20s| %-20s | %-20s |\n", firstName, lastName, email, userType);
		}
		System.out.println();
	}

	private void createNewUser() {
		boolean validArguments = false;
		
		while(!validArguments) {
			
			System.out.println("Please fill in the following data of the new user:\n");
			System.out.print("First Name: ");
			String firstName = scanner_.nextLine();
			System.out.print("Last Name: ");
			String lastName = scanner_.nextLine();
			System.out.print("Email: ");
			String email = scanner_.nextLine();
			System.out.print("Password: ");
			String password = scanner_.nextLine();
			System.out.println("User role: ");
			System.out.print("( Admin / Seller / Supplier ): ");
			String userType = scanner_.nextLine();
			
			System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
			System.out.println("Creating a new user...\n");
			System.out.println(viewFunctions_.getSeperator() + "\n");
			
			try {
				boolean res = usersController_.createNewUser(firstName, lastName, password, email, userType);
				if(res) {
					System.out.println("User: '" + email.toLowerCase() + "' succesfully created.\n");
					System.out.println(viewFunctions_.getSeperator() + "\n");
					validArguments = true;
				} else {
					System.out.println("! User: '" + email.toLowerCase() + "' is already exists.\n\nUser details: ");
					String userDetails = usersController_.getUserAsString(email);
					printUserDetails(userDetails);
				}
				
			} catch( Exception e) {
				System.out.println(e.getMessage() + "\n");
				System.out.println(viewFunctions_.getSeperator() + "\n");
			}
		}
		//end while
	}
	
	private void deleteUser() {
		boolean validArguments = false;
		
		//show users table
		showUsersTable();
		System.out.println(viewFunctions_.getSeperator() + "\n");
		while(!validArguments) {
			System.out.println("Which user would you like to delete?");
			System.out.print("User email: ");
			String email = scanner_.nextLine();
			
			try{
				if(usersController_.deleteUser(email)) {
					System.out.println("\n" +viewFunctions_.getSeperator() + "\n");
					System.out.println("User: '" + email.toLowerCase() + "' succesfully deleted.");
					System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
					validArguments = true;
				} else {
					System.out.println("\n" + viewFunctions_.getSeperator());
					System.out.println("\n! User: '" + email.toLowerCase() + "' does not exists. Please try again.\n");
					System.out.println(viewFunctions_.getSeperator() + "\n");
				}
			} catch(Exception e) {
				System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
				System.out.println(e.getMessage());
				System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
			}
		}
		
	}
	
	private void printUserDetails(String user) {

		String userName = splitString(user, "::", 0);
		String email = splitString(user, "::", 2);
		String userType = splitString(user, "::", 3);
		
		System.out.print("First Name: " + splitString(userName,"_", 0) + "\n");
		System.out.print("Last Name:  " + splitString(userName,"_", 1) + "\n");
		System.out.print("Email:      " + email + "\n");
		System.out.print("Role:       " + convertUserType(Integer.parseInt(userType)) + "\n");

	}
	
	private String splitString(String text,String delimiter, int index) {
		int delimiterLength = delimiter.length();
		int endIndex = delimiterLength * -1;
		int beginIndex = 0;
		int i = 0;
		while(i++ <= index) {
			beginIndex = endIndex + delimiterLength;
			endIndex = text.indexOf(delimiter, beginIndex);
			if(endIndex == -1)
				endIndex = text.length();
		}
		return text.substring(beginIndex, endIndex);
	}
	
	private String convertUserType(int userType) {
		switch(userType) {
		case 1:
			return "Admin";
		case 2:
			return "Seller";		
		case 3:
			return "Supplier";
		default:
			return "User role is invalid";
		}
	}
	
	private void showUsersManagerMenu(){
		
		System.out.println();
		System.out.println(viewFunctions_.showProgressBar("", prevScreens_));
		
		System.out.println("Which action would you like to take?\n");
		System.out.println("View all users         ========> 1");
		System.out.println("Create a new user      ========> 2");
		System.out.println("Delete existing user   ========> 3");
		System.out.println();
		System.out.println("Back to main menu      ========> -1");
		System.out.println("Save and logout        ========> 0");
		System.out.println();	
	}
}
