package com.store.view;

public abstract class Menu {
	protected ViewFunctions viewFunctions_; 
	
	public Menu() {
		viewFunctions_ = new ViewFunctions();
	}
	
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
	
	protected abstract int mainMenu();
	protected abstract int showSelectedScreen(int command);
}
