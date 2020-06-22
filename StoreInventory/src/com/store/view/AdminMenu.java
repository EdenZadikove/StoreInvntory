package com.store.view;

import java.io.IOException;
import java.util.Scanner;

import com.store.controller.Controller;

public class AdminMenu {
	private ViewFunctions viewFunctions_; 
	private StoreAdmin storeAdmin_;
	private OrdersAdmin ordersAdmin_;
	private Users users_;
	private Controller controller_;
	private Scanner scanner_;
	
	public AdminMenu() throws IOException {
		viewFunctions_ = ViewFunctions.getInstance();
		storeAdmin_ = new StoreAdmin();
		ordersAdmin_ = new OrdersAdmin();
		users_ = new Users();
		controller_ = new Controller();
		scanner_ = new Scanner(System.in);
	}
	
	public void menuManager() throws IOException {
		int command = 0; //initialize variable
		int showMainMenuAgain = -1; //-1 - show main menu, 0 - Logout
		while(showMainMenuAgain == -1) { // while user want to show menu again
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);	
		} 
		if(showMainMenuAgain == 0 && command != 0) //command arrived from other screen
			showMainMenuAgain = showSelectedScreen(0);
	}

	private int mainMenu() throws IOException {
		int command = -1;

		System.out.println("\n" + viewFunctions_.getMainMenuHeader() +"\n");
		System.out.println(viewFunctions_.showProgressBar("", "Main Menu"));
		System.out.println("Which screen would you like to get?\n");
		System.out.println("Store Manager    ========> 1");
		System.out.println("Orders Manager   ========> 2");
		System.out.println("Users Manager    ========> 3");
		System.out.println("Logout           ========> 0");
		System.out.print("\nI want to view: ");
		
		command = scanner_.nextInt();
		scanner_.nextLine(); //ignore enter char
		
		viewFunctions_.validateInsertedData(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again.");
		return command;
	}
	
	private int showSelectedScreen(int command) throws IOException {
		int showMainMenuAgain = 0;  //0- logout, -1 - show menu again
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