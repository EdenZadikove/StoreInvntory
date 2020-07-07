package com.store.view;

import java.util.ArrayList;

public class OrdersAdmin extends Orders {
	
	public OrdersAdmin(){
		super();
	}
	
	public int showMenu(){
		
		int command = -1;
		int validCommandFlag = 0;
		boolean firstTimeMenuFlag = true; //If menu is printed for the first time- no need to print a separator
		boolean showOrdersManagerMenu_again = false; //true- show again, false- don't show again

		System.out.println(viewFunctions_.getOrderMenuHeader());
		
		while(validCommandFlag == 0) {
			if(!firstTimeMenuFlag) System.out.println(viewFunctions_.getSeperator());
			showOrdersManagerMenu();
			firstTimeMenuFlag = false;
			
			System.out.println();
			command = viewFunctions_.validateIntInput("I want to: ");
			command = viewFunctions_.validateInsertedData(1,5,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showOrdersManagerMenu_again = actionNavigate(command); //command choice from Orders Manager Menu
			
			if(!showOrdersManagerMenu_again) {  //showOrdersManagerMenu_again == false
				if(command == -1) {
					System.out.println("Going back to Main Menu...");
				}
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
	
	private void showOrdersManagerMenu() {
		System.out.println();
		viewFunctions_.showProgressBar(viewFunctions_.getPrevScreens(), "Order Manager Menu");
		System.out.println("Which action would you like to take?\n");
		System.out.println("View orders table      ========> 1");
		System.out.println("Create a new order     ========> 2");
		System.out.println("Cancel pending order   ========> 3");
		System.out.println("Delete existing order  ========> 4");			
		System.out.println("Edit pending order     ========> 5");
		System.out.println();
		System.out.println("Back to main menu      ========> -1");
		System.out.println("Save and Logout        ========> 0");
	}
	
	private boolean actionNavigate(int command){
		boolean result = false; //if result == true then show menu again
		boolean isEmptyOrders = false; //not empty
		String prevScreensTemp = viewFunctions_.getPrevScreens() + " -----> " + "Orders Manager Menu";
		int res = -1;
		switch(command) {
		case 1: //View orders table
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "View orders table"));
			showOrdersTable("all", "Orders Table:\n", "No orders. Create a new order and come back to see it here :)\n");
			result = true;
			break;
		case 2: //Create a new order
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "Create a new order"));
			createOrder(); 
			result = viewFunctions_.anotherActions();
			break;
		case 3: //Cancel pending order 
			System.out.println(viewFunctions_.getSeperator() + "\n");
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "Cancel pending order"));
			isEmptyOrders = isEmptyMap("pending");
			if(!isEmptyOrders) {
				res = cancelOrder(); 
				if(res == 0) result = true; //show menu again
				else result = viewFunctions_.anotherActions();
			} else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}		
			break;
		case 4: //Delete existing order
			System.out.println(viewFunctions_.getSeperator() + "\n");
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "Delete existing order"));
			isEmptyOrders = isEmptyMap("all");
			if(!isEmptyOrders) {
				res = deleteOrder();
				if(res == 0) result = true;
				else result = viewFunctions_.anotherActions();
			} else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}
			break;
		case 5: //Edit pending order
			System.out.println(viewFunctions_.getSeperator());
			System.out.println();
			System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "Edit pending order"));
			isEmptyOrders = isEmptyMap("pending");
			if(!isEmptyOrders) {
				res = editOrder();
				if(res == 0) result = true;
				else result = viewFunctions_.anotherActions();
			}else {
				System.out.println();
				result = viewFunctions_.anotherActions();
			}
			break;
		default: //Exit screen, for case 0 and -1
			ordersController_.saveToFileOrders();
			break;
		}
		return result;
	}
	
	private void createOrder(){
		int item = -1;
		int quantity = 0;
		
		System.out.println("Which item would you like to order?\n");
		showItemsMenu();
		System.out.println();
		item = viewFunctions_.validateIntInput("I would like to order: ");
		item = viewFunctions_.validateInsertedData_noZeroOne(1, 6, item, "I would like to order: ", "! Invalid item. Please try again"); 
		
		//print selected item details
		System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
		System.out.println("Selected item details: \n");
		printProductsDetails(ProductsEnum.values()[item-1]);
		System.out.println();
		
		//Ask user how many items
		boolean validInputFlag = false;
		int orderId = 0;
		quantity = viewFunctions_.validateIntInput("How many items would you like to order? "); 

		while(!validInputFlag) {
			try {
				orderId = ordersController_.createOrder(ProductsEnum.values()[item-1].toString(), quantity);
				validInputFlag = true;
			} catch(IllegalArgumentException e) {
				System.out.println("\n" + e.getMessage());
				quantity = viewFunctions_.validateIntInput("I would like to order: ");
			}
		}
		
		//Print order summary 
		System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
		System.out.println("Order successfully created!\n");
		System.out.println("Order Summery:");
		System.out.println("Order ID:     " + orderId);
		System.out.println("Item Name:    " + ProductsEnum.values()[item-1]);
		System.out.println("Quantity:     " + quantity);
		System.out.println("Order Status: pending");
		System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
	}
	
	private void showItemsMenu() {
		System.out.println(calcString(ProductsEnum.Gloves.toString(), 1));
		System.out.println(calcString(ProductsEnum.Coat.toString(), 2));
		System.out.println(calcString(ProductsEnum.Scarf.toString(), 3));
		System.out.println(calcString(ProductsEnum.Swimsuit.toString(), 4));
		System.out.println(calcString(ProductsEnum.Tshirt.toString(), 5));
		System.out.println(calcString(ProductsEnum.Dress.toString(), 6));
	}
	
	private String calcString(String productStr, int index) {
		final int LENGTH = 11 - productStr.length();
		for(int i = 0; i <= LENGTH; i++) 
			productStr += " ";
		return productStr + "========>  " + index;	
	}
	
	private void printProductsDetails(ProductsEnum p) {
		ArrayList <String> details;
		details = storeController_.getProductDetails(p.toString());
		System.out.println("Item name:             " + details.get(0));
		System.out.println("Item session:          " + details.get(1));
		System.out.println("Item price (per unit): " + details.get(2) + "$");
		System.out.println("Available in stock:    " + details.get(3) + " units");
	}
	
	private int cancelOrder() {
		String introduction = "\n1.  Pay attention- you can only cancel orders with status 'pending'\n" + 
							  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu\n" +
							  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID";
		return actions(introduction, "cancel", 0, 1, "pending");
	}
	
	private int deleteOrder() {
		String introduction = "\n1.  Pay attention: - you can only delete orders with status 'denied', 'approved' or 'canceled'\n" + 
				  "\n                   - By deleting order you won't see it in the table anymore.\n" +
				  "\n                   - This action can not be undo!\n" + 
				  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu.\n" +
				  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID.";
		return actions(introduction, "delete", 0, 1, "all");
	}
	
	private int editOrder() {
		String introduction = "\n1.  Pay attention: - you can only edit orders with status 'pending'\n" + 
				  "\n                   - This action can not be undo!\n" +
				  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu.\n" + 
				  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID.";
		return actions(introduction, "edit", 0, 1, "pending");
	}
}
