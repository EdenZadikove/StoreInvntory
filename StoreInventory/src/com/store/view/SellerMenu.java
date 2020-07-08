package com.store.view;

import com.store.controller.UserSessionServiceController;

public class SellerMenu extends Menu {
	private UserSessionServiceController userSessionService_ ;
	private StoreSeller storeSeller_;
	
	public SellerMenu() {
		super();
		userSessionService_ = new UserSessionServiceController();
		storeSeller_ = new StoreSeller();
	}
	
	@Override
	public void menuManager(){
		int command = 0;
		int showMainMenuAgain = -1; //-1 - show main menu
		while(showMainMenuAgain == -1) {
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);
		}
		if(showMainMenuAgain == 0 && command != 0) //command arrived from other screen
			showMainMenuAgain = showSelectedScreen(0);
	}
	
	@Override
	protected int mainMenu() {
		int command = -1;

		System.out.println("\n" + viewFunctions_.getMainMenuHeader() + "\n");
		System.out.println(viewFunctions_.showProgressBar("", "Main Menu"));
		System.out.println("Which screen would you like to get?\n");

		System.out.println("Store Manager  ========> 1");
		System.out.println("Logout         ========> 0");
		System.out.println();
		
		command = viewFunctions_.validateIntInput("I want to view: ");
		command = viewFunctions_.validateInsertedData_noZeroOne(0, 1, command, "I want to view: " ,"! Invalid choice. Please try again. ");
		return command;
	}
	
	@Override
	protected int showSelectedScreen(int command){
		int showMainMenuAgain = 0; //0- don't show again, -1- show again
		switch(command) {
		case 0:
			System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
			System.out.println("Logout...");
			userSessionService_.logout();
			break;
		case 1:
			System.out.println();
			showMainMenuAgain = storeSeller_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}
}
