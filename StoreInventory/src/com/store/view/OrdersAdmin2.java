package com.store.view;

import java.util.ArrayList;

public class OrdersAdmin2 extends Orders {
	
	public OrdersAdmin2(){
		super();
	}
	
	public int showMenu() {
		String userSelection = "-1";
		boolean stayInOrders = true;
		boolean firstTimeMenuFlag = true;
		
		System.out.println(viewFunctions_.getOrderMenuHeader());
		if(!firstTimeMenuFlag) {
			System.out.println(viewFunctions_.getSeperator() + "\n");
			firstTimeMenuFlag = false;
		}
		while (stayInOrders) {
			showOrdersManagerMenu();
			userSelection = scanner_.nextLine();
			navigateAction(userSelection);
		}
		
		
		
	}
	
	private boolean navigateAction(String userSelection) {
		String prevScreensTemp = viewFunctions_.getPrevScreens() + " -----> " + "Orders Manager Menu";
		boolean stayInOrders = true;
		
		switch(userSelection) {
		case "-1":
			ordersController_.saveToFileOrders();
			stayInOrders = false;
			break;
		case "0":
			ordersController_.saveToFileOrders();
			stayInOrders = false;
			break;
		case "1":
			System.out.println(viewFunctions_.getSeperator() + "\n\n" + viewFunctions_.showProgressBar(prevScreensTemp, "View orders table"));
			showOrdersTable("all", "Orders Table:\n", "No orders. Create a new order and come back to see it here :)\n");
			stayInOrders = true;
			break;
		case "2":
			System.out.println(viewFunctions_.getSeperator() + "\n\n" + viewFunctions_.showProgressBar(prevScreensTemp, "Create a new order"));
			createOrder();
			stayInOrders = viewFunctions_.anotherActions();
			break;
		case "3":
			System.out.println(viewFunctions_.getSeperator() + "\n\n" + viewFunctions_.showProgressBar(prevScreensTemp, "Delete existing order"));
			cancelOrder();
			break;
		case "4":
			break;
		case "5":
			break;
		}
		
		
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
		
		quantity = viewFunctions_.validateIntInput("How many items would you like to order? "); 
		boolean validInputFlag = false;
		int orderId = 0;
		while(!validInputFlag) {
			try {
				orderId = ordersController_.createOrder(ProductsEnum.values()[item-1].toString(), quantity);
				validInputFlag = true;
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				quantity = viewFunctions_.validateIntInput("I would like to order: ");
			}
		}
		 
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
		//check if there are orders to cancel
		boolean isEmptyOrders = isEmptyMap("pending");
		if(isEmptyOrders) {
			//orders table is not empty
			System.out.println("! Action can not be taken. Only pending orders in the table.\n");
			System.out.println(viewFunctions_.getSeperator());
		}
		else {
			//Orders table is NOT empty
			String introduction = "\n1.  Pay attention- you can only cancel orders with status 'pending'\n" + 
					  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu\n" +
					  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID";
			actions(introduction, "cancel", 0, 1, "pending");
		}
	}
}