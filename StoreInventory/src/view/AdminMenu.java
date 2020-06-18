package view;

import java.io.IOException;
import java.util.Scanner;

import controller.Controller;

public class AdminMenu {
	private StoreAdmin storeAdmin_;
	private OrdersAdmin ordersAdmin_;
	private Users users_;
	private Controller controller_;
	private ViewFunctions viewFunctions_; 
	
	public AdminMenu() throws IOException {
		storeAdmin_ = new StoreAdmin();
		ordersAdmin_ = new OrdersAdmin();
		users_ = new Users();
		controller_ = new Controller();
		viewFunctions_ = new ViewFunctions();
	}
	
	public void menuManager() throws IOException {
		int command = 0;
		int showMainMenuAgain = -1; //1 - show main menu, 0 - Logout
		while(showMainMenuAgain == -1) {
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);	
		}
		if(showMainMenuAgain == 0 && command != 0) //command arrived from other screen
			showMainMenuAgain = showSelectedScreen(0);
	}

	private int mainMenu() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int command = -1;

		System.out.println("\n-----------------------------------------Main Menu------------------------------------------\n");
		System.out.println("|  Progress bar:  Main Menu                                                                 |\n");
		System.out.println("Which screen would you like to get?\n");
		
		System.out.println("Store Manager    ========> 1");
		System.out.println("Orders Manager   ========> 2");
		System.out.println("Users Manager    ========> 3");
		System.out.println("Logout           ========> 0");
		
		System.out.println();
		System.out.print("I want to view: ");
		
		command = scanner.nextInt();
		scanner.nextLine(); //ignore enter char
		
		viewFunctions_.validateInsertedData(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again.");
		return command;
	}
	
	
	private int showSelectedScreen(int command) throws IOException {
		int showMainMenuAgain = 0;  //logout
		switch(command) {
		case 0:
			System.out.println("Logout...");
			controller_.logout();
			break;
		case 1:
			showMainMenuAgain = storeAdmin_.showMenu(); //-1 - show again. 0- logout
			break;
		case 2:
			showMainMenuAgain = ordersAdmin_.showMenu(); //-1 - show again. 0- logout
			break;
		case 3:
			users_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}
}
