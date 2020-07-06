package com.store.view;

import com.store.controller.UserSessionServiceController;

public class SupplierMenu implements Menu {
	private OrdersSupplier ordersSupplier_;
	private UserSessionServiceController userSessionService_;
	private ViewFunctions viewFunctions_;
	
	public SupplierMenu() {
		ordersSupplier_ = new OrdersSupplier();
		userSessionService_ = new UserSessionServiceController();
		viewFunctions_ = new ViewFunctions();
	}
	
	@Override
	public void menuManager() {
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
		command = viewFunctions_.validateIntInput("I want to view: ");
		command = validateInsertedData(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again. ");
		return command;
	}
	
	private int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		while (!(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			command = viewFunctions_.validateIntInput(text);
		}
		System.out.println(); // print enter
		return command;
	}
	
	private int showSelectedScreen(int command) {
		int showMainMenuAgain = 0; //0- don't show again, -1- show again
		switch(command) {
		case 0:
			System.out.println("Logout...");
			userSessionService_.logout();
			break;
		case 1:
			showMainMenuAgain = ordersSupplier_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}
}