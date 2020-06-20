package view;

import java.io.IOException;
import java.util.Scanner;

public class StoreSeller extends Store {
	private String prevScreens_;
	
	public StoreSeller() throws IOException {
		super();
		prevScreens_ = viewFunctions_.getPrevScreens() + " -----> " + "Store Manager Menu";
	}

	public int showMenu() throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		int command = -1; 
		int validCommandFlag = 0;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		boolean showStoreManagerMenu_again = false; //true- show again, false- don't show again
		
		System.out.println(viewFunctions_.getStoreMenuHeader());
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions_.getSeperator());
			showStoreSellerMenu();
			firstTimeMenuFlag = false;
			
			System.out.println(); //enter
			System.out.print("I want to: ");
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			command = viewFunctions_.validateInsertedData(1,2,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showStoreManagerMenu_again = actionNavigate(command); //command choice from Store Manager Menu
			
			if(!showStoreManagerMenu_again) {  //showStoreManagerMenu_again == false
				if(command == -1)
					System.out.println("Going back to Main Menu...");
				else { 
					command = 0; //for logout
					actionNavigate(0); //exit the program
				}
				validCommandFlag = 1; //end loop
			}
		}
		//end while
		return command;
	}
	
	private void showStoreSellerMenu() {
		System.out.println();
		viewFunctions_.showProgressBar(viewFunctions_.getPrevScreens(), "Store Manager Menu");
		
		System.out.println("Which action would you like to take?\n");
		System.out.println("View store inventory   ========> 1");
		System.out.println();
		System.out.println("Back to main menu      ========> -1");
		System.out.println("Logout                 ========> 0");
	}
	
	
	private boolean actionNavigate(int command) throws IOException {
		boolean result = false; //if result == true then show menu again
		boolean isEmptyStore = false; //not empty
		switch(command) {
		case 1: //View store inventory
			System.out.println(viewFunctions_.getSeperator());
			System.out.println(viewFunctions_.showProgressBar(prevScreens_, "View store inventory"));
			showProductsTable(1, "Store Inventory:", " "); 
			result = true;
			break;
		default: //Exit screen, for case 0 and -1
		}
		return result;
	}
}
