package com.store.view;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class StoreAdmin extends Store {
	private String prevScreens_;
	private ArrayList <String> productsArray_ = new ArrayList<String>();
	
	public StoreAdmin(){
		super();
		prevScreens_ = viewFunctions_.getPrevScreens() + " -----> " + "Store Manager Menu";
	}
	
	public int showMenu(){
		int command = -1; 
		int validCommandFlag = 0;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		boolean showStoreManagerMenu_again = false; //true- show again, false- don't show again
		
		System.out.println(viewFunctions_.getStoreMenuHeader());
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions_.getSeperator());	
			showStoreManagerMenu();
			firstTimeMenuFlag = false; //not the first time anymore
			
			command = viewFunctions_.validateIntInput("I want to: ");
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
	
	private boolean actionNavigate(int command){
		boolean result = false; //if result == true then show menu again
		boolean emptyStore = false;
		int res = -1;

		switch(command) {
		case 1: //View pending orders table
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "View store inventory"));
			showProductsTable("Store Inventory:", "! Empty Store. Create new orders to fill the store :)"); 
			result = true;
			break;
		case 2: //Edit product price 
			System.out.println(viewFunctions_.getSeperator());
			System.out.println("\n" + viewFunctions_.showProgressBar(prevScreens_, "Edit product price"));
			
			emptyStore = isEmptyStore();
			if(!emptyStore) {
				System.out.println(viewFunctions_.getInstructionsHeader());
				res = actions("edit price");
				if(res == 0) result = true;//item is 0, user want to show menu again
				else result = viewFunctions_.anotherActions();
			} else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}
			break;
		case 3: //Remove product
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreens_, "Remove product"));
			emptyStore = isEmptyStore();
			if(!emptyStore) {
				System.out.println(viewFunctions_.getInstructionsHeader());
				res = actions("remove");
				if(res == 0) result = true;//if res == 0, user want to show menu again
				else result = viewFunctions_.anotherActions();
			} else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}
			
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
		System.out.println("Save and logout        ========> 0");
		System.out.println();
	}

	
	private int actions(String action){
		Map<String, String> productsMap_ = new Hashtable<String, String>();
		productsMap_ = getProducts();
		
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
			System.out.println(calcCounterStr("Which item would you like to " + action + "?", itemsCounter));
			showProducts(productsMap_);
			System.out.println();
			itemNumber = viewFunctions_.validateIntInput("Item number: ");
			itemNumber = validateInsertedData(1, productsMap_.size(), itemNumber, "Item number: ", "! Invalid item number. Please try again." );
			if(itemNumber == 0) {	//user want to exit
				System.out.println("Going back to Store Manager Menu...");
				break; //exit while
			}	
			
			String msg = "";
			boolean actionResault = false;
			String itemName = productsArray_.get(itemNumber - 1); //get item name
			String product = productsMap_.get(itemName); //get the product as a string
			boolean validInputFlag = false;
			switch(action) {
			case "edit price":
				double oldPrice = Double.parseDouble( StringUtils.substringBetween(product, "Price:", ";"));
				System.out.println(viewFunctions_.getSeperator() + "\n");
				System.out.println("Selected item: " + itemName);
				System.out.println("Current price (per unit): " + oldPrice + "$");
				double newPrice = viewFunctions_.validateDoubleInput("Set a new price: ");
				while(!validInputFlag) {
					try{
						actionResault = storeController_.editPrice(itemName, newPrice, oldPrice);
						validInputFlag = true;
					} catch(Exception e) {
						System.out.println(e.getMessage());
						newPrice = viewFunctions_.validateDoubleInput("Set a new price: ");
					}
				}
				if(actionResault)
					msg = itemName +" price successfully updated from " + oldPrice +"$ to " +newPrice +"$";
				else msg = "! Something went wrong. Please contact your system administrator.";
				break;
			case "remove":
				System.out.println(viewFunctions_.getSeperator() + "\n");
				System.out.println("Are you sure you want to delete " + itemName + "?\n");
				System.out.println("Yes  ========> 1");
				System.out.println("No   ========> 0");

				int choice = viewFunctions_.validateIntInput("\nMy choice: ");
				itemNumber = viewFunctions_.validateInsertedData_noZeroOne(0,1, choice, "My choice: ", "! Invalid choice. Please try again.");
				if(itemNumber == 1) { //user want to delete item
					actionResault = storeController_.removeProduct(itemName);
					if(actionResault)
						msg = itemName +" succesffully removed from the store";
					else msg = "! Something went wrong. Please contact your system administrator.";
				}
				else {//user don't want to delete item
					System.out.println();
					timesToLoop--;
					itemsCounter++;
				}
				break; //end while
			}
			if(itemNumber != 0) {
				System.out.println("\n" + viewFunctions_.getSeperator() + "\n\n" + msg + "\n");
				System.out.println(viewFunctions_.getSeperator() + "\n"); //logout
				if(msg.contains("successfully")) {
					timesToLoop--;
					itemsCounter++;
				}
			}
		}
		//end while
		return itemNumber;
	}
	
	private void showProducts(Map<String, String> productsMap_){
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
	
	private int howManyTimes(int timesToLoop, int itemNumber, int productsCounter, String title) {
		System.out.println(title);
		timesToLoop = viewFunctions_.validateIntInput("Number of items: ");
		timesToLoop = validateInsertedData(1, productsCounter, timesToLoop, "Items number: ", "! Items range is: 1 to " + productsCounter);
		return timesToLoop;
	}
	
	
	private int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		/* If 0 --->  don't do anything */		
		while ((command != 0 ) &&  !(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			command = viewFunctions_.validateIntInput(text);
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