package view;

import java.io.IOException;
import java.util.Scanner;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public class StoreAdmin extends Store {
	private String prevScreens;
	public StoreAdmin() throws IOException {
		super();
		prevScreens = viewFunctions.getPrevScreens() + " -----> " + "Store Manager Menu";
	}
	
	public int showMenu() throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		int command = -1; 
		int validCommandFlag = 0;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		boolean showStoreManagerMenu_again = false; //true- show again, false- don't show again
		
		System.out.println(screenHeader);
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions.getSeperator());
			showStoreManagerMenu();
			firstTimeMenuFlag = false;
			
			System.out.println(); //enter
			System.out.print("I want to: ");
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			command = viewFunctions.validateInsertedData(1,2,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
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
		boolean isEmptyStore = false; //not empty
		switch(command) {
		case 1: //View pending orders table
			System.out.println(viewFunctions.getSeperator());
			viewFunctions.showProgressBar(prevScreens, "View store inventory" );
			showProductsTable(1, "Store Inventory:", " "); 
			result = true;
			break;
		case 2:
			System.out.println(viewFunctions.getSeperator());
			System.out.println();
			viewFunctions.showProgressBar(prevScreens, "Edit product price");
			System.out.println(viewFunctions.getInstructionsHeader());
			res = editProductPrice();
			if(res == 0) result = true;//item is 0, user want to show menu again
			else result = viewFunctions.anotherActions();
		default: //Exit screen, for case 0 and -1
			storeController_.saveToFile();
		}
		return result;
	}
	
	
	private void showStoreManagerMenu() {
		System.out.println();
		viewFunctions.showProgressBar(viewFunctions.getPrevScreens(), "Store Manager Menu");
		
		System.out.println("Which action would you like to take?\n");
		System.out.println("View store inventory   ========> 1");
		System.out.println("Edit product price     ========> 2");
		System.out.println();
		System.out.println("Back to main menu      ========> -1");
		System.out.println("Save and Logout        ========> 0");
	}

	
	private int editProductPrice() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int itemNumber = 0; //if itemNumber == 0----> go back to Store main menu
		int timesToLoop = 1; // if timesToLoop == -1 then show again
		int productsCounter = productsMap.size();
		String introduction = "\n1.  Press '0' in any stage if you want to exit and go back to Store Manager Menu\n";
		
		System.out.println(introduction);
		System.out.println(viewFunctions.getSeperator() + "\n");
		
		//If productsCounter == 1, then no need to ask how many times I want to do the action
		if(productsCounter != 1) {
			timesToLoop = howManyTimes(timesToLoop, itemNumber, productsCounter);
			if(timesToLoop == 0)//user want to exit
				System.out.println("Going back to Store Manager Menu...\n");
		}
			
		while(timesToLoop > 0){
			
			System.out.println("For each item would you like to set a new price?\n");
			showProducts();
			System.out.println();
			System.out.print("Item number: ");
			itemNumber = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			itemNumber = validateInsertedData(1, productsMap.size(), itemNumber, "Item number: ", "! Invalid item number. Please try again." );
			if(itemNumber == 0) {
				//user want to exit
				System.out.println("Going back to Store Manager Menu...\n");
				break;
			}	
			
			String msg = "";
			String itemName = productsArray[itemNumber-1];
			String product = productsMap.get(itemName);
			String oldPrice = StringUtils.substringBetween(product, "Price:", ";");
			
			System.out.println("Selected item: " + itemName);
			System.out.println("Current price (per unit): " + oldPrice);
			System.out.print("Set a new price: ");
			double newPrice = scanner.nextDouble();
			scanner.nextLine();
			
			newPrice  = validatePrice(oldPrice, newPrice, itemName);
			msg = storeController_.editPrice(itemName, newPrice);
			
			System.out.println();
			System.out.println(msg);
			System.out.println();
			if(msg.contains("successfully")) {
				timesToLoop--;
				System.out.println(viewFunctions.getSeperator());
				System.out.println();
			}
		}
		//end while
		return itemNumber;
	}
	
	
	private void showProducts() {
		int i = 1;
		for (Entry<String, String> entry : productsMap.entrySet()) {
			String itemName = StringUtils.substringBetween(entry.getValue(), "ItemName:", ";");
			System.out.println(calcStr(itemName, i++));
		}
	}
	
	
	private String calcStr(String itemName, int option) {
		final int LENGTH = 13;
		String str = itemName;
		for(int i = 0; i < LENGTH-itemName.length(); i++) 
			str = str + " ";
		str = str + "  ========>  " + option;
		return str;
	}
	
	
	private int howManyTimes(int timesToLoop, int itemNumber, int productsCounter) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many items do you want to edit price?");
		System.out.print("Items number: ");
		timesToLoop = scanner.nextInt();
		timesToLoop = validateInsertedData(1, productsCounter, timesToLoop, "Items number: ", "! Items range is: 1 to " + productsCounter);
		return timesToLoop;
	}
	
	
	private double validatePrice(String oldPrice,double newPrice, String itemName) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		int validFlag = 0;
		String msg;
		while (validFlag == 0) {
			msg = ""; //reset
			if(String.valueOf(newPrice).equals(oldPrice)) {
				msg = "! " + itemName + " price is already " + String.valueOf(newPrice);
			}
			else if((int)newPrice <= 0)
				msg = "! " + itemName + " price must be more then 0";
			if(msg.length() > 0) { //something is wrong. do again
				System.out.println(msg);
				System.out.println();
				System.out.print("Set a new price: ");
				newPrice = scanner.nextDouble();
				scanner.nextLine();
			}
			else validFlag = 1; //valid input
		}		
		getProducts(); //update map in view package
		return newPrice;
	}
	
	
	private int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		/* If 0 --->  don't do anything */		
		Scanner scanner = new Scanner(System.in);
		while ((command != 0 ) &&  !(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		System.out.println(); // print enter
		return command;
	}
}