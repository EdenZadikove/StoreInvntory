package view;

import java.io.IOException;
import java.util.Scanner;

import model.UsersDB;

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
	

	private ViewFunctions() {
		welcomMenuHeader_ = setHeader(LENGTH - "Welcome To Store Inventory System!".length(), "Welcome To Store Inventory System!");
		seperator_ = setHeader(LENGTH, "");
		instructionsHeader_ = setHeader(LENGTH-"INSTRUCTIONS".length(), "INSTRUCTIONS");
		loginHeader_ = setHeader(LENGTH-"Login".length(), "Login");
		mainMenuHeader_ = setHeader(LENGTH - "Main Menu".length(), "Main Menu");
		storeMenuHeader_ = setHeader(LENGTH - "Store Manager Menu".length(), "Store Manager Menu");
		orderMenuHeader_ = setHeader(LENGTH - "Order Manager Menu".length(), "Order Manager Menu");
	}
	
	//Singletone
		public static ViewFunctions getInstance() {
			if (instance_ == null) {
				instance_ = new ViewFunctions();
			}
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

	

	public String setHeader(int length, String header) {
		String newHeader = "";
		for(int i = 0; i < length/2 ; i++)//left side
			newHeader += "-";
		newHeader  += header; //header
		int test  = (int) Math.ceil(length/2);
		int test2 = (int) Math.ceil(length/2.0);
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
		 * If -1 --->  don't do anything
		 *  */		
		Scanner scanner = new Scanner(System.in);
		while ((command != 0 && command != -1) &&  !(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		System.out.println(); // print enter
		return command;
	}
	
	
	public double validateInsertedData(int start, int end, double command, String text, String errorMsg) {
		/* If 0 --->  don't do anything
		 * If -1 --->  don't do anything
		 *  */		
		Scanner scanner = new Scanner(System.in);
		while ((command != 0 && command != -1) &&  !(command <= end && command >= start)) {
			System.out.println(); // enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		return command;
	}
	
	public int validateInsertedData_noZeroOne(int start, int end, int command, String text, String errorMsg) {		
		Scanner scanner = new Scanner(System.in);
		while (!(command <= end && command >= start)) {
			System.out.println(); // enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		return command;
	}
	
	//showMenuAgain
	public boolean anotherActions() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Would you like to take another action?\n");
		System.out.println("Yes, show me the menu please    ========> 1");
		System.out.println("No, I'm done                    ========> 0\n");
		System.out.print("My choice: ");

		int command = scanner.nextInt();
		scanner.nextLine(); //ignore enter char
		
		command = validateInsertedData_noZeroOne(0, 1, command, "My choice: ", "! Invalid choice. Please try again"); 
		return command == 1; //true - show menu again
	}
}
