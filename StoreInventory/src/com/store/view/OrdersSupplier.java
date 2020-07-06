package com.store.view;

public class OrdersSupplier extends Orders {

	public OrdersSupplier() {
		super();
		ordersList_ = ordersController_.getOrders("pending");
	}
	
	public int showMenu() {
		int command = -1;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		int validCommandFlag = 0;
		boolean showOrdersManagerMenu_again = false; //true- show again, false- don't show again
	
		System.out.println(viewFunctions_.getOrderMenuHeader());
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions_.getSeperator());
			showOrdersManagerMenu();
			firstTimeMenuFlag = false;
			System.out.println();
			command = viewFunctions_.validateIntInput("I want to: ");
			command = viewFunctions_.validateInsertedData(1,3,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showOrdersManagerMenu_again = actionNavigate(command); //command choice from Orders Manager Menu
			
			if(!showOrdersManagerMenu_again) {  //showOrdersManagerMenu_again == false
				if(command == -1) {
					System.out.println("Going back to Main Menu...");
				}
				else {
					command = 0; //for logout
					actionNavigate(0); //Logout
				}
				validCommandFlag = 1; //end loop
			}
		}
		//end while
		return command;
	}
	
	private void showOrdersManagerMenu() {
		System.out.println();
		System.out.println(viewFunctions_.showProgressBar(viewFunctions_.getPrevScreens(), "Orders Manager Menu"));		
		System.out.println("Which action would you like to take?\n");
		System.out.println("View pending orders table    ========> 1");
		System.out.println("Approved order               ========> 2");
		System.out.println("Denied order                 ========> 3");
		System.out.println();
		System.out.println("Back to main menu            ========> -1");
		System.out.println("Logout                       ========> 0");
		
	}
	
	private boolean actionNavigate(int command) {
		boolean result = false; //if result == true then show menu again
		String prevScreensTemp = viewFunctions_.getPrevScreens() + " -----> " + "Orders Manager Menu";
		int res = -1;
		boolean isEmptyOrders = false; //not empty
		switch(command) {
		case 1: //View pending orders table
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "View pending orders table"));
			showOrdersTable("pending", "Pending Orders Table:\n", "No pending orders"); //filtered table
			result = true;
			break;
		case 2: //Approved order
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "Approved order"));
			isEmptyOrders = isEmptyMap("pending");
			if(!isEmptyOrders) {
				res = navigateAction("approved");
				if(res == 0) result = true; //show menu again
				else result = viewFunctions_.anotherActions();
			}  else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}	
			break;
		case 3: //Denied order 
			System.out.println(viewFunctions_.getSeperator());
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "Denied order"));
			isEmptyOrders = isEmptyMap("pending");
			if(!isEmptyOrders) {
				res = navigateAction("denied"); 
				if(res == 0) result = true;
				else result = viewFunctions_.anotherActions();
			} else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}		
			break;
		default: //Exit screen for cases -1 and 0
			ordersController_.saveToFileOrders();
			break;
		}
		return result;
	}
	
	private int navigateAction(String action)  { //return orderId

		String introduction = "\n1.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu\n" +
				  "\n2.  Press '-1' in any stage if you want show orders table in order to choose order ID";
		if(action.equals("approved")) 
			return actions(introduction, "approved", 1,3, "pending"); 
		return actions(introduction, "denied", 1, 3, "pending");
	}
}

