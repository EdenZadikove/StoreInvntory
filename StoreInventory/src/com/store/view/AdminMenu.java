package com.store.view;

import com.store.controller.UserSessionServiceController;

public class AdminMenu implements Menu{
	private ViewFunctions viewFunctions_; 
	private StoreAdmin storeAdmin_;
	private OrdersAdmin ordersAdmin_;
	private Users users_;
	private UserSessionServiceController userSessionServiceController_;
	
	public AdminMenu(){
		viewFunctions_ = new ViewFunctions();
		storeAdmin_ = new StoreAdmin();
		ordersAdmin_ = new OrdersAdmin();
		users_ = new Users();
		userSessionServiceController_ = new UserSessionServiceController();
	}
	
	@Override
	public void menuManager(){
		int command = 0; //initialize variable
		int showMainMenuAgain = -1; //-1 - show main menu, 0 - Logout
		while(showMainMenuAgain == -1) { // while user want to show menu again
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);	
		} 
		if(showMainMenuAgain == 0 && command != 0) //command arrived from other screen
			showMainMenuAgain = showSelectedScreen(0);
	}

	private int mainMenu(){
		int command = -1;

		System.out.println("\n" + viewFunctions_.getMainMenuHeader() +"\n");
		System.out.println(viewFunctions_.showProgressBar("", "Main Menu"));
		System.out.println("Which screen would you like to get?\n");
		System.out.println("Store Manager    ========> 1");
		System.out.println("Orders Manager   ========> 2");
		System.out.println("Users Manager    ========> 3");
		System.out.println("Logout           ========> 0");
		System.out.println();
		command = viewFunctions_.validateIntInput("I want to view: "); 
		command = viewFunctions_.validateInsertedData_noZeroOne(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again.");
		System.out.println();
		return command;
	}
	
	private int showSelectedScreen(int command){
		int showMainMenuAgain = 0;  //0- logout, -1 - show menu again
		switch(command) {
		case 0:
			System.out.println("Logout...");
			userSessionServiceController_.logout();
			break;
		case 1:
			showMainMenuAgain = storeAdmin_.showMenu(); //-1 - show again. 0- logout
			break;
		case 2:
			showMainMenuAgain = ordersAdmin_.showMenu(); //-1 - show again. 0- logout
			break;
		case 3:
			showMainMenuAgain = users_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}
}