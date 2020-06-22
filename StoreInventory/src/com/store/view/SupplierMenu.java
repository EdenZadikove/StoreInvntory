package com.store.view;

import java.io.IOException;
import java.util.Scanner;

import com.store.controller.Controller;

public class SupplierMenu {
	private OrdersSupplier ordersSupplier_;
	private Controller controller_;
	private ViewFunctions viewFunctions_;
	private Scanner scanner_;
	
	public SupplierMenu() throws IOException {
		ordersSupplier_ = new OrdersSupplier();
		controller_ = new Controller();
		viewFunctions_ = ViewFunctions.getInstance();
		scanner_ = new Scanner(System.in);
	}
	
	public void menuManager() throws IOException {
		int command = 0;
		int showMainMenuAgain = -1; //-1 - show main menu
		while(showMainMenuAgain == -1) {
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);
		}
		if(showMainMenuAgain == 0 && command != 0) //command arrived from other screen
			showMainMenuAgain = showSelectedScreen(0);
	}
	
	private int mainMenu() {
		int command = -1;
		System.out.println("\n" +  viewFunctions_.getMainMenuHeader() +"\n");
		System.out.println(viewFunctions_.showProgressBar("", "Main Menu"));
		System.out.println("Which screen would you like to get?\n");
		System.out.println("Orders Manager ========> 1");
		System.out.println("Logout         ========> 0");
		
		System.out.println();
		System.out.print("I want to view: ");
		
		command = scanner_.nextInt();
		scanner_.nextLine(); //ignore enter char
		
		command = validateInsertedData(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again. ");
		return command;
	}
	
	private int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		
		while (!(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner_.nextInt();
			scanner_.nextLine(); //ignore enter char
		}
		System.out.println(); // print enter
		return command;
	}
	
	private int showSelectedScreen(int command) throws IOException {
		int showMainMenuAgain = 0; //0- don't show again, -1- show again
		switch(command) {
		case 0:
			System.out.println("Logout...");
			controller_.logout();
			break;
		case 1:
			showMainMenuAgain = ordersSupplier_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}
}