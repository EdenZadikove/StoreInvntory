package com.store.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class StoreAdmin extends Store {
	private String prevScreens_;
	private ArrayList <String> productsArray_ = new ArrayList<String>();
	
	public StoreAdmin() throws IOException {
		super();
		prevScreens_ = viewFunctions_.getPrevScreens() + " -----> " + "Store Manager Menu";
	}
	
	public int showMenu() throws IOException {
		int command = -1; 
		int validCommandFlag = 0;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		boolean showStoreManagerMenu_again = false; //true- show again, false- don't show again
		
		System.out.println(viewFunctions_.getStoreMenuHeader());
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions_.getSeperator());	
			showStoreManagerMenu();
			firstTimeMenuFlag = false; //not the first time anymore
			
			System.out.print("I want to: ");
			command = scanner_.nextInt();
			scanner_.nextLine(); //ignore enter char
			
			command = viewFunctions_.validateInsertedData(1,3,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
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
	
	private boolean actionNavigate(int command) throws IOException {
		boolean result = false; //if result == true then show menu again
		int res = -1;
		//boolean isEmptyStore = false; //not empty
		switch(command) {
		case 1: //View pending orders table
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "View store inventory"));
			showProductsTable(1, "Store Inventory:", " "); 
			result = true;
			break;
		case 2:
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "Edit product price"));
			System.out.println(viewFunctions_.getInstructionsHeader());
			res = actions("edit price"); 
			if(res == 0) result = true;//item is 0, user want to show menu again
			else result = viewFunctions_.anotherActions();
			break;
		case 3:
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreens_, "Remove product"));
			System.out.println(viewFunctions_.getInstructionsHeader());
			res = actions("remove");
			if(res == 0) result = true;//item is 0, user want to show menu again
			else result = viewFunctions_.anotherActions();
			break;
		default: //Exit screen, for case 0 and -1
			storeController_.saveToFileStore();
		}
		return result;
	}
	
	private void showStoreManagerMenu() {
		System.out.println();
		System.out.println(viewFunctions_.showProgressBar("", prevScreens_));
		
		System.out.println("Which action would you like to take?\n");
		System.out.println("View store inventory   ========> 1");
		System.out.println("Edit product price     ========> 2");
		System.out.println("Remove procudt         ========> 3");
		System.out.println();
		System.out.println("Back to main menu      ========> -1");
		System.out.println("Save and Logout        ========> 0");
	}

	
	private int actions(String action) throws IOException {
		int itemNumber = 0; //if itemNumber == 0----> go back to Store main menu
		int timesToLoop = 1; // if timesToLoop == -1 then show again
		int productsCounter = productsMap_.size();
		String introduction = "\nPress '0' in any stage if you want to exit and go back to Store Manager Menu\n";
		
		System.out.println(introduction);
		System.out.println(viewFunctions_.getSeperator() + "\n");
		
		//If productsCounter == 1, then no need to ask how many times I want to do the action
		if(productsCounter != 1) {
			String title = "Please insert the number of items you would like to remove.";
			if(action == "edit price")
				title ="Please insert the number of items you would like to change their price to.";
			
			timesToLoop = howManyTimes(timesToLoop, itemNumber, productsCounter, title);
			if(timesToLoop == 0)//user want to exit
				System.out.println("Going back to Store Manager Menu...\n");
			else
				System.out.println(viewFunctions_.getSeperator() + "\n");
		}
		
		int itemsCounter = 1;
		
		while(timesToLoop > 0){
			getProducts(); //Synchronized with DB
		
			System.out.println(calcCounterStr("Which item would you like to edit price?", itemsCounter));
			showProducts();
			System.out.print("\nItem number: ");
			itemNumber = scanner_.nextInt();
			scanner_.nextLine(); //ignore enter char
			itemNumber = validateInsertedData(1, productsMap_.size(), itemNumber, "Item number: ", "! Invalid item number. Please try again." );
			if(itemNumber == 0) {	//user want to exit
				System.out.println("Going back to Store Manager Menu...\n");
				break; //exit while
			}	
			
			String msg = "";
			String itemName = productsArray_.get(itemNumber - 1); //get item name
			String product = productsMap_.get(itemName); //get the product as a string
			switch(action) {
			case "edit price":
				String oldPrice = StringUtils.substringBetween(product, "Price:", ";");
				System.out.println("Selected item: " + itemName);
				System.out.println("Current price (per unit): " + oldPrice + "$");
				System.out.print("Set a new price: ");
				
				double newPrice = scanner_.nextDouble();
				scanner_.nextLine();
				
				newPrice  = validatePrice(oldPrice, newPrice, itemName);
				msg = storeController_.editPrice(itemName, newPrice);
				break;
			case "remove":
				System.out.println("Are you sure you want to delete " + itemName + "?");
				System.out.println("Yes  ========> 1");
				System.out.println("No   ========> 0");
				System.out.print("My choice: ");
				
				int choice = scanner_.nextInt();
				scanner_.nextLine();
				
				itemNumber = viewFunctions_.validateInsertedData_noZeroOne(0,1, choice, "My choice: ", "! Invalid choice. Please try again.");
				if(itemNumber == 1) msg = storeController_.removeProduct(itemName);
				else {
					System.out.println();
					timesToLoop--;
					itemsCounter++;
				}
				break; //end while
			}
			if(itemNumber != 0) {
				System.out.println("\n" + msg + "\n");
				if(msg.contains("successfully")) {
					timesToLoop--;
					itemsCounter++;
					//System.out.println(viewFunctions_.getSeperator() + "\n");
				}
			}
			
			System.out.println(viewFunctions_.getSeperator() + "\n"); //logout
		}
		//end while
		return itemNumber;
	}
	
	
	private void showProducts() throws IOException {
		int i = 1;
		for (Entry<String, String> entry : productsMap_.entrySet()) {
			String itemName = StringUtils.substringBetween(entry.getValue(), "ItemName:", ";");
			productsArray_.add(itemName);
			System.out.println(calcStr(itemName, i++));
		}
	}
		
	//create a string of product for the products menu. e.g: shirt =====> 1
	private String calcStr(String itemName, int option) {
		final int LENGTH = 13;
		String str = itemName;
		for(int i = 0; i < LENGTH-itemName.length(); i++) 
			str = str + " ";
		str = str + "  ========>  " + option;
		return str;
	}
	
	private int howManyTimes(int timesToLoop, int itemNumber, int productsCounter, String title) throws IOException {
		System.out.println(title);
		System.out.print("Number of items: ");
		timesToLoop = scanner_.nextInt();
		timesToLoop = validateInsertedData(1, productsCounter, timesToLoop, "Items number: ", "! Items range is: 1 to " + productsCounter);
		return timesToLoop;
	}
	
	private double validatePrice(String oldPrice,double newPrice, String itemName) throws IOException {
		int validFlag = 0;
		String errMsg;
		while (validFlag == 0) {
			errMsg = ""; //reset
			if(String.valueOf(newPrice).equals(oldPrice)) {
				errMsg = "\n! " + itemName + " price is already " + String.valueOf(newPrice);
			}
			else if((int)newPrice <= 0)
				errMsg = "\n! " + itemName + " price must be more then 0";
			if(errMsg.length() > 0) { //something is wrong. do again
				System.out.println(errMsg);
				System.out.print("Set a new price: ");
				newPrice = scanner_.nextDouble();
				scanner_.nextLine();
			}
			else validFlag = 1; //valid input
		}		
		getProducts(); //update map in view package
		return newPrice;
	}
	
	
	private int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		/* If 0 --->  don't do anything */		
		while ((command != 0 ) &&  !(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner_.nextInt();
			scanner_.nextLine(); //ignore enter char
		}
		System.out.println(); // print enter
		return command;
	}
	
	private String calcCounterStr(String title, int counter) {
		String loopCounterStr = "Item number: " + counter + "\n";
		int rowLength = 115 - loopCounterStr.length() - title.length();
		for(int i = 0; i< rowLength; i++) {
			title += " ";
		}
		return title += loopCounterStr;
	}
}