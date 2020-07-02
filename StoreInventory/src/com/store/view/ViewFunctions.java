package com.store.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewFunctions {
	/*Functions:
	 * showProgressBar()
	 * validateInsertedData()
	 * showMenuAgain()
	 * */
	private static ViewFunctions instance_ = null;
	private final int LENGTH = 114;
	
	private String prevScreens_ = "Main Menu";
	private String seperator_;
	private String welcomMenuHeader_;
	private String loginHeader_;
	private String mainMenuHeader_;
	private String storeMenuHeader_;
	private String orderMenuHeader_;
	private String instructionsHeader_;
	private Scanner scanner_;
	

	private ViewFunctions() {
		welcomMenuHeader_ = setHeader(LENGTH - "Welcome To Store Inventory System!".length(), "Welcome To Store Inventory System!");
		seperator_ = setHeader(LENGTH, "");
		instructionsHeader_ = setHeader(LENGTH-"INSTRUCTIONS".length(), "INSTRUCTIONS");
		loginHeader_ = setHeader(LENGTH-"Login".length(), "Login");
		mainMenuHeader_ = setHeader(LENGTH - "Main Menu".length(), "Main Menu");
		storeMenuHeader_ = setHeader(LENGTH - "Store Manager Menu".length(), "Store Manager Menu");
		orderMenuHeader_ = setHeader(LENGTH - "Orders Manager Menu".length(), "Order Manager Menu");
		scanner_ = new Scanner(System.in);
	}
	
	//Singletone
	public static ViewFunctions getInstance() {
			if (instance_ == null)
				instance_ = new ViewFunctions();
			return instance_;
		} 
	
	public String getLoginHeader() {
		return loginHeader_;
	}

	public void setLoginHeader(String loginHeader_) {
		this.loginHeader_ = loginHeader_;
	}

	public String getPrevScreens() {
		return prevScreens_;
	}

	public String getSeperator() {
		return seperator_;
	}

	public String getWelcomMenuHeader() {
		return welcomMenuHeader_;
	}

	public String getMainMenuHeader() {
		return mainMenuHeader_;
	}

	public String getStoreMenuHeader() {
		return storeMenuHeader_;
	}

	public String getOrderMenuHeader() {
		return orderMenuHeader_;
	}

	public String getInstructionsHeader() {
		return instructionsHeader_;
	}

	public String setHeader(int length, String header) { // make a string in the following format- "---HEADER---"
		String newHeader = "";
		for(int i = 0; i < length/2 ; i++)//left side
			newHeader += "-";
		newHeader  += header; //header
		for(int i = 0; i <  (int) Math.ceil(length/2) ; i++) //right side. round up in not even
			newHeader += "-";
		return newHeader;
	}
	
	public String showProgressBar(String prevScreens, String currentScreen) {
		String fullProgreeBar = "|  Progress bar: ";
		if(prevScreens.length() == 0) fullProgreeBar += currentScreen;
		else fullProgreeBar = "|  Progress bar: " + prevScreens +  " -----> " + currentScreen;
		
		int fullProgreeBarSize = fullProgreeBar.length();
		int lengthDeff = LENGTH - fullProgreeBarSize;
		
		for(int i = 0; i < lengthDeff ; i++) {
			fullProgreeBar = fullProgreeBar + " ";
		}
		fullProgreeBar = fullProgreeBar + "|\n";
		return fullProgreeBar;
	}
	
	public int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		/* If 0 --->  don't do anything
		 * If -1 --->  don't do anything */		
		while ((command != 0 && command != -1) &&  !(command <= end && command >= start)) {
			System.out.println("\n" + errorMsg); // user insert wring data.
			command = validateIntInput(text);
		}
		System.out.println(); // print enter
		return command;
	}
	
	//override for double input
	public double validateInsertedData(int start, int end, double command, String text, String errorMsg) {
		/* If 0 --->  don't do anything
		 * If -1 --->  don't do anything */
		while ((command != 0 && command != -1) &&  !(command <= end && command >= start)) {
			System.out.println("\n" + errorMsg);
			System.out.print(text);	
			command = scanner_.nextDouble();
			scanner_.nextLine(); //ignore enter char
		}
		return command;
	}
	
	//validate data for input without 0 and -1 (0 and -1 need to be checked)
	public int validateInsertedData_noZeroOne(int start, int end, int command, String text, String errorMsg) {
		while (!(command <= end && command >= start)) {
			System.out.println("\n" + errorMsg);
			//System.out.print(text);	
			command = validateIntInput(text);
		}
		
		return command;
	}
	
	//ask the user if he want to do another action
	public boolean anotherActions() {
		System.out.println("Would you like to take another action?\n");
		System.out.println("Yes, show me the menu please    ========> 1");
		System.out.println("No, I'm done                    ========> 0\n");

		int command = validateIntInput("My choice: ");
		command = validateInsertedData_noZeroOne(0, 1, command, "My choice: ", "! Invalid choice. Please try again");
		System.out.println();
		return command == 1; //true - show menu again, false- logout
	}
	
	//catch exception for scannerInt, check id user insert integer.
	public int validateIntInput(String text) {
		boolean validFlag = false;
		int num = -1;
		while(!validFlag) {
			try {
				System.out.print(text);
				num = scanner_.nextInt();
				validFlag = true;
				
			} catch(InputMismatchException e) {
				System.out.println("\n! Invalid input. Please insert numbers only.");
				scanner_.nextLine();
			}	
		}
		return num;
	}
	
	public double validateDoubleInput(String text) {
		boolean validFlag = false;
		double num = -1;
		while(!validFlag) {
			try {
				System.out.print(text);
				num = scanner_.nextDouble();
				validFlag = true;
				
			} catch(InputMismatchException e) {
				System.out.println("\n! Invalid input. Please insert numbers only.");
				scanner_.nextLine();
			}	
		}
		return num;
	}
}