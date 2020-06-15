package view;

import java.io.IOException;
import java.util.Scanner;

public class OrdersSupplier extends Orders {
	
	public OrdersSupplier() throws IOException {
		super();
	}
	
	public int showMenu() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int command = -1;
		int validCommandFlag = 0;
		boolean showOrdersManagerMenu_again = false; //true- show again, false- don't show again
	
		while(validCommandFlag == 0) {
			
			showOrdersManagerMenu();
			
			System.out.print("I want to: ");
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			command = validateInsertedData(-1,3,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showOrdersManagerMenu_again = actionNavigate(command); //command choice from Orders Manager Menu
			
			if(!showOrdersManagerMenu_again) {  //showOrdersManagerMenu_again == false
				if(command == -1) {
					System.out.println("Going back to Main Menu...\n");
				}
				else {
					System.out.println("Exit the program...\n");
					actionNavigate(0); //exit the program
				}
				validCommandFlag = 1; //end loop
			}
		}
		//end while
		return command;
	}
	
	private void showOrdersManagerMenu() {
		String screenHeader = "\n------------------------------------Orders Manager Menu-------------------------------------\n";
		System.out.println(screenHeader);
		showProgressBar(prevScreens, "Order Manager Menu");
		
		System.out.println("Which action would you like to take?\n");

		System.out.println("View pending orders table    ========> 1");
		System.out.println("Approved order               ========> 2");
		System.out.println("Denied order                 ========> 3");
		System.out.println();
		System.out.println("Back to main menu            ========> -1");
		System.out.println("Exit the program             ========> 0");
		
	}
	
	private boolean actionNavigate(int command) throws IOException {
		boolean result = false; //if result == true then show menu again
		String prevScreensTemp = prevScreens + " -----> " + "Orders Manager Menu";
		int res = -1;
		switch(command) {
		case 1: //View pending orders table
			showProgressBar(prevScreensTemp, "View pending orders table" );
			showOrdersTable(1, "\nPending Orders Table:\n", "\nNo Pending Orders\n"); //filtered table
			result = showMenuAgain();
			break;
		case 2: //Approved order
			showProgressBar(prevScreensTemp, "Approved order");
			navigateAction("approved"); 
			result = showMenuAgain();
			break;
		case 3: //Denied order 
			showProgressBar(prevScreensTemp, "Denied order");
			res = navigateAction("denied"); 
			if(res == 0) result = true;
			else result = showMenuAgain();
			break;
		default: //Exit screen
			ordersController_.saveToFile();
			break;
		}
		return result;
	}
	
	private int navigateAction(String action) throws IOException {
		String introduction = "\n1.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu\n" +
				  "\n2.  Press '-1' in any stage if you want show orders table in order to choose order ID";
		if(action.equals("approved")) return actions(introduction, "approved", 1); 
		return actions(introduction, "denied", 1);
		
	}

}

