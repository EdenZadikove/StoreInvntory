package com.store.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import com.store.controller.OrdersController;

public abstract class Orders{
	protected ViewFunctions viewFunctions_; 
	protected OrdersController ordersController_;
	protected ArrayList<String> ordersList_ = new ArrayList<String>();
	protected Scanner scanner_;
	
	public Orders () throws IOException {
		super();
		viewFunctions_ = ViewFunctions.getInstance();
		ordersController_ = new OrdersController();
		scanner_ = new Scanner(System.in);
	}
	
	protected void commandIsZeroOrNegetiveOne(int command, int userType) throws IOException{
		String prevScreensTemp = viewFunctions_.getPrevScreens() + " -----> " + "Orders Manager Menu";
		
		if(command == 0) System.out.println("\nGoing back to Orders Manager Menu...\n");
		else if(command == -1) {
			switch(userType) {
				case 1: //admin
					System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
					System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "View pending orders table" ));
					showOrdersTable("all", "Orders Table:\n", "No orders. Create a new order and come back to see it here :)\n");
					System.out.println(viewFunctions_.getSeperator());
					break;
				case 3: //supplier
					System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
					System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "View pending orders table" ));
					showOrdersTable("pending", "Pending orders Table:\n", "No pending orders\n");
					System.out.println(viewFunctions_.getSeperator() + "\n");
			}
		}
	}
	
	protected void showOrdersTable(String filterStatus, String tableHeader, String emptyTableHeader) throws IOException {
		ordersList_ = ordersController_.getOrders(filterStatus);
		if(ordersList_.size() == 0) System.out.println(emptyTableHeader);
		else {
			System.out.println(tableHeader);
			System.out.printf("| %-20s| %-20s | %-20s | %-20s | %-20s |\n","Order ID", "Item Name", "Quantity", "Order Status", "Order Date");
			for(String order : ordersList_) {
				String OrderId = StringUtils.substringBetween(order, "OrderId:", ";");
				String ItemName = StringUtils.substringBetween(order, "ItemName:", ";");
				String Quantity = StringUtils.substringBetween(order, "Quantity:", ";");
				String OrderStatus = StringUtils.substringBetween(order, "OrderStatus:", ";");
				String OrderDate = StringUtils.substringBetween(order, "OrderDate:", ";");
				System.out.printf("| %-20s| %-20s | %-20s | %-20s | %-20s |\n",OrderId, ItemName, Quantity, OrderStatus, OrderDate);
			}
		}
		System.out.println();
	}
	
	protected int actions(String introduction, String action, int isFilterd, int userType, String filter) throws IOException {	
		/*validate:
		 * If 0 ---> go back to Orders Manager Menu
		 * If -1 ---> show orders table
		 * If  times range is 1 ---> don't show "How many items do you want to" */
		
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		int timesToLoop = 1; // if timesToLoop == -1 then show again
		int itemsCounter = ordersController_.itemsCounterByFilter(filter);
		System.out.println(viewFunctions_.getInstructionsHeader());
		System.out.println(introduction + "\n");
		System.out.println(viewFunctions_.getSeperator() +"\n");
		
		if(itemsCounter != 1) {
			//If itemsCounter == 1, then no need to ask how many times I want to do the action
			while (timesToLoop == -1) {
				System.out.println("How many items do you want to " + action + "?");
				timesToLoop = viewFunctions_.validateIntInput("Number of items: ");
				timesToLoop = viewFunctions_.validateInsertedData(1, itemsCounter, timesToLoop, "Number of items: ", "! Items range is: 1 to " + itemsCounter);
				commandIsZeroOrNegetiveOne(timesToLoop, userType);
			}
		}

		while(timesToLoop > 0 ) {
			System.out.println("Which order would you like to " + action + "?");
			orderId = getOrderIdFromUser(filter, "Order id: ", action);   ///controller validate
			commandIsZeroOrNegetiveOne(orderId, userType);

			while(orderId == -1) {
				System.out.println("\nWhich order would you like to " + action +"?");
				orderId = getOrderIdFromUser(filter, "Order id: ", action);
				commandIsZeroOrNegetiveOne(orderId, userType);
			}
			if(orderId == 0) break;	 //user want to stop and go back to orders-main-menu
			int quantity = 1;
			if(action == "edit") {
				System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
				quantity = viewFunctions_.validateIntInput("Update order quantity to: ");
				//quantity = viewFunctions_.validateInsertedData(1, 100, quantity, "Update order quantity to: ", "! Quantity must be between 1 to 100");
				commandIsZeroOrNegetiveOne(quantity, userType);
				while(quantity == -1) { //if quantity == -1, then user want to view orders table
					quantity =  viewFunctions_.validateIntInput("Update order quantity to: ");
					//quantity = viewFunctions_.validateInsertedData(1, 100, quantity, "Update order quantity to: ", "! Quantity must be between 1 to 100");
					commandIsZeroOrNegetiveOne(quantity, userType);
					System.out.println();
				}
				if(quantity == 0) {
					orderId = quantity;
					break;
				}
			}
			
			String msg = "";
			boolean res = false;
			switch(action) {
				case "delete":
					try {
						res = ordersController_.deleteOrder(orderId);
						if(res)
							msg = "Order id- " + orderId + " successfully deleted";
					} catch(Exception e) {
						msg = e.getMessage();
					}
					break; 
				case "cancel":
					try {
						res = ordersController_.cancelOrder(orderId);
						if(res)
							msg = "Order id- " + orderId + " successfully canceled";
					}catch(Exception e) {
						msg = e.getMessage();
					}
					
					break;
				case "edit":
					try {
						msg = ordersController_.editOrder(orderId, quantity);
					}
					catch(IllegalArgumentException e) {
						msg = e.getMessage();
						//catchFlag = 1;
						
					}
					break;	
				default:
					msg = ordersController_.changeOrderStatus(orderId, action);
					break;
			}
			
			System.out.println();
			if(msg.contains("approved") && !msg.contains("!") )
				msg +=" \nStore inventory updated.";
			System.out.println(viewFunctions_.getSeperator() + "\n\n" + msg + "\n\n" + viewFunctions_.getSeperator() +"\n");
			
			if(msg.contains("successfully")) timesToLoop--;
		}
		//end while
		return orderId;
	}

	
	protected boolean isEmptyMap(String filter) {
		if(ordersController_.itemsCounterByFilter(filter) == 0) { //empty orders map
			System.out.println("! Action can not be taken. No " + filter + " orders in the table.");
			System.out.println();
			System.out.println(viewFunctions_.getSeperator());
			return true;
		}
		return false;
	}
	
	private int getOrderIdFromUser(String statusFilter, String text,  String action) throws IOException {
		boolean continueFlag = true;
		int orderId = -1;
		while(continueFlag) {
			orderId = viewFunctions_.validateIntInput(text);
			try {
				if(orderId != 0 && orderId != -1) {
					if (!ordersController_.isOrderExists(orderId, statusFilter, action)) {
						System.out.println("\n! Order does not exists. Please try again.");	
					}
					else continueFlag = false;
				}
				else continueFlag = false;
			} catch(Exception e) {
				System.out.println("\n" + e.getMessage());	
			}
		}
		return orderId;
	}
}