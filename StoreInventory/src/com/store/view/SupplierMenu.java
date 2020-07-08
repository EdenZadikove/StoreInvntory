package com.store.view;

import com.store.controller.UserSessionServiceController;

public class SupplierMenu extends Menu {
	private OrdersSupplier ordersSupplier_;
	private UserSessionServiceController userSessionService_;
	
	public SupplierMenu() {
		super();
		ordersSupplier_ = new OrdersSupplier();
		userSessionService_ = new UserSessionServiceController();
	}
	
	@Override
	protected int mainMenu() {
		int command = -1;
		System.out.println("\n" +  viewFunctions_.getMainMenuHeader() +"\n");
		System.out.println(viewFunctions_.showProgressBar("", "Main Menu"));
		System.out.println("Which screen would you like to get?\n");
		System.out.println("Orders Manager ========> 1");
		System.out.println("Logout         ========> 0");
		System.out.println();
		command = viewFunctions_.validateIntInput("I want to view: ");
		command = viewFunctions_.validateInsertedData_noZeroOne(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again. ");
		return command;
	}
	
	@Override
	protected int showSelectedScreen(int command) {
		int showMainMenuAgain = 0; //0- don't show again, -1- show again
		switch(command) {
		case 0:
			System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
			System.out.println("Logout...");
			userSessionService_.logout();
			break;
		case 1:
			System.out.println();
			showMainMenuAgain = ordersSupplier_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}
}