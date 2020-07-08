package com.store.view;

import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import com.store.controller.OrdersController;
import com.store.controller.StoreController;

public abstract class Orders{
	protected ViewFunctions viewFunctions_; 
	protected OrdersController ordersController_;
	protected StoreController storeController_;
	protected ArrayList<String> ordersList_ = new ArrayList<String>();
	protected Scanner scanner_;
	
	public Orders (){
		super();
		viewFunctions_ = new ViewFunctions();
		ordersController_ = new OrdersController();
		storeController_ = new StoreController();
		scanner_ = new Scanner(System.in);
	}
	
	protected void commandIsZeroOrNegetiveOne(int command, int userType){
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
	
	protected void showOrdersTable(String filterStatus, String tableHeader, String emptyTableHeader){
		try {
			ordersList_ = ordersController_.getOrders(filterStatus);
		} catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
			 System.exit(1); //exit with failure
		}
		
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
	
	protected int actions(String introduction, String action, int isFilterd, int userType, String filter) {	
		/*validate:
		 * If 0 ---> go back to Orders Manager Menu
		 * If -1 ---> show orders table
		 * If  times range is 1 ---> don't show "How many items do you want to" */
		
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		int timesToLoop = 1; // if timesToLoop == -1 then show again
		int itemsCounter = 0;
		boolean printSeperator = true;
		String msg = "";
				
		if(action.equals("delete"))
			itemsCounter = (ordersController_.itemsCounterByFilter("approved")) + (ordersController_.itemsCounterByFilter("denied")) + (ordersController_.itemsCounterByFilter("canceled"));
		else{
			try {
				itemsCounter = ordersController_.itemsCounterByFilter(filter);
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				 System.exit(1); //exit with failure
			}
		}
		
		System.out.println(viewFunctions_.getInstructionsHeader());
		System.out.println(introduction + "\n");
		System.out.println(viewFunctions_.getSeperator() +"\n");
		
		if(itemsCounter != 1) {
			//If itemsCounter == 1, then no need to ask how many times I want to do the action
			timesToLoop = -1;
			while (timesToLoop == -1) {
				System.out.println("How many items would you like to " + action + "?");
				timesToLoop = viewFunctions_.validateIntInput("Number of items: ");
				timesToLoop = viewFunctions_.validateInsertedData(1, itemsCounter, timesToLoop, "Number of items: ", "! Items range must be between 1 to " + itemsCounter);
				commandIsZeroOrNegetiveOne(timesToLoop, userType);
			}
		} 
		
		int counter = 1;
		if(timesToLoop == 1)
			printSeperator = false;
		while(timesToLoop > 0 ) {
			if(!msg.isEmpty() || printSeperator)
					System.out.println(viewFunctions_.getSeperator() +"\n");
			String title = "Which order would you like to " + action + "?";
			System.out.println(calcCounterStr(title, counter));
			
			orderId = viewFunctions_.validateIntInput("Order Id: "); 
			commandIsZeroOrNegetiveOne(orderId, userType);

			while(orderId == -1) {
				System.out.println("\n" + calcCounterStr(title, counter));
				orderId = viewFunctions_.validateIntInput("Order Id: "); 
				commandIsZeroOrNegetiveOne(orderId, userType);
			}
			
			if(orderId == 0) break;	 //user want to stop and go back to orders-main-menu
			
			int quantity = 1;
			
			if(action == "edit") {
				System.out.println("\n" + viewFunctions_.getSeperator() + "\n");
				quantity = viewFunctions_.validateIntInput("Update order quantity to: ");
				commandIsZeroOrNegetiveOne(quantity, userType);
				while(quantity == -1) { //if quantity == -1, then user want to view orders table
					quantity =  viewFunctions_.validateIntInput("Update order quantity to: ");
					commandIsZeroOrNegetiveOne(quantity, userType);
					System.out.println();
				}
				if(quantity == 0) {
					orderId = quantity;
					break;
				}
			}
			
			
			switch(action) {
			case "delete":
				try {
					if(ordersController_.deleteOrder(orderId))
						msg = "Order id- " + orderId + " successfully deleted";
					else msg = "! Order id- " + orderId + " does not exists. Please try again.";
				} catch(Exception e) {
					msg = e.getMessage();
				}
				break; 
			case "cancel":
				try {
					if(ordersController_.cancelOrder(orderId))
						msg = "Order id- " + orderId + " successfully canceled";
					else msg = "! Order id- " + orderId + " does not exists. Please try again.";
				}catch(Exception e) {
					msg = e.getMessage();
				}
				break;
			case "edit":
				try {
					if(ordersController_.editOrder(orderId, quantity))
						msg = "Order id- " + orderId + " quantity successfully updated to " + quantity + "units.";
					else
						msg = "! Order id- " + orderId + " does not exists. Please try again.";
				}catch(Exception e) {
					msg = e.getMessage();
				}
				break;	
			default: //approved or denied
				try {
					if(ordersController_.changeOrderStatus(orderId, action))
						msg = "Order ID - " + orderId + " is successfully updated from 'pending' to " + "'" + action + "'.";
				}catch(Exception e) {
					msg = e.getMessage();
				}
				break;
			}
			System.out.println();
			if(msg.contains("approved") && !msg.contains("!") )
				msg +=" \nStore inventory updated.";
			System.out.println(viewFunctions_.getSeperator() + "\n\n" + msg + "\n");
			
			if(msg.contains("successfully")) {
				timesToLoop--;
				counter++;
			}
		}
		//end while
		return orderId;
	}

	
	protected boolean isEmptyMap(String filter) {
		String msg = "No " + filter + " orders in the table.";
		if(filter.equals("all")) {
			if((ordersController_.itemsCounterByFilter("approved") != 0 || 
					ordersController_.itemsCounterByFilter("denied") != 0 ||
					ordersController_.itemsCounterByFilter("canceled") != 0)) {
				return false;
			}
			if(ordersController_.itemsCounterByFilter("pending") != 0)
				msg = "You only have orders with status 'pending'.";
			else
				msg = "Orders table is empty.";
			
		}
		
		else if(ordersController_.itemsCounterByFilter(filter) != 0) {
			return false; //not empty orders map
		}
		System.out.println("! Action can not be taken. " + msg + "\n");
		System.out.println(viewFunctions_.getSeperator());
		return true;
	}
	
	private String calcCounterStr(String title, int counter) {
		String loopCounterStr = "Item number: " + counter;
		int rowLength = 114 - loopCounterStr.length() - title.length();
		for(int i = 0; i< rowLength; i++) {
			title += " ";
		}
		return title += loopCounterStr;
	}
}