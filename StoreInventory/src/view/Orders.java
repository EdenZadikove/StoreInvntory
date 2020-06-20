package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import controller.OrdersController;
import model.Model;

public abstract class Orders{
	protected ViewFunctions viewFunctions_; 
	protected OrdersController ordersController_;
	protected ArrayList<String> ordersList_ = new ArrayList<String>();
	
	public Orders () throws IOException {
		super();
		viewFunctions_ = ViewFunctions.getInstance();
		ordersController_ = new OrdersController();
	}
	
	
	protected void commandIsZeroOrNegetiveOne(int command, int userType) throws IOException{
		String prevScreensTemp = viewFunctions_.getPrevScreens() + " -----> " + "Orders Manager Menu";
		
		if(command == 0) {
			System.out.println("Going back to Orders Manager Menu...\n");
		} 
		else if(command == -1) {
			switch(userType) {
			case 1: //admin
				System.out.println();
				System.out.println(viewFunctions_.getSeperator());
				System.out.println();
				System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "View pending orders table" ));
				
				showOrdersTable(0, "Orders Table:\n", "No orders. Create a new order and come back to see it here :)\n");
				System.out.println(viewFunctions_.getSeperator());
				
				break;
			case 3: //supplier
				System.out.println();
				System.out.println(viewFunctions_.getSeperator());
				System.out.println();
				System.out.println(viewFunctions_.showProgressBar(prevScreensTemp, "View pending orders table" ));
				showOrdersTable(1, "Pending orders Table:\n", "No pending orders\n");
				System.out.println(viewFunctions_.getSeperator());
			}
		}
	}
	

	protected void showOrdersTable(int isFiltered, String tableHeader, String emptyTableHeader) throws IOException {
	
		ordersList_ = ordersController_.getOrders(isFiltered);
		
		if(ordersList_.size() == 0) {
			System.out.println(emptyTableHeader);
		} else {
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
		 * If  times range is 1 ---> don't show "How many items do you want to"
		 * */
		
		Scanner scanner = new Scanner(System.in);
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		int timesToLoop = 1; // if timesToLoop == -1 then show again
		int itemsCounter = ordersController_.itemsCounterByFilter(filter);
		System.out.println(viewFunctions_.getInstructionsHeader());
		System.out.println(introduction);
		System.out.println(viewFunctions_.getSeperator());
		
		
		if(itemsCounter != 1) {
			//If itemsCounter == 1, then no need to ask how many times I want to do the action
			while (timesToLoop == -1) {
				String str = "\nHow many items do you want to " + action + "?";
				System.out.println(str);
				timesToLoop = scanner.nextInt();
				timesToLoop = viewFunctions_.validateInsertedData(1, itemsCounter, timesToLoop, "", "! Items range is: 1 to " + itemsCounter);
				commandIsZeroOrNegetiveOne(timesToLoop, userType);
			}
		}

		while(timesToLoop > 0 ) {
			System.out.println();
			System.out.println("Which item would you like to " + action +"?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			orderId = validateOrder(orderId);
			commandIsZeroOrNegetiveOne(orderId, userType);
			

			while(orderId == -1) {
				System.out.println("\nWhich item would you like to " + action +"?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
				orderId = validateOrder(orderId);
				commandIsZeroOrNegetiveOne(orderId, userType);
			}
			
			if(orderId == 0) break;
			
			int quantity = 1;
			if(action == "edit") {
				System.out.print("Update order quantity to: ");
				quantity = scanner.nextInt();
				scanner.nextLine();
				quantity = viewFunctions_.validateInsertedData(1, 100, quantity, "Update order quantity to: ", "! Quantity must be between 1 to 100");
				commandIsZeroOrNegetiveOne(quantity, userType);
				while(quantity == -1) {
					System.out.print("Update order quantity to: ");
					quantity =  scanner.nextInt();
					quantity = viewFunctions_.validateInsertedData(1, 100, quantity, "Update order quantity to: ", "! Quantity must be between 1 to 100");
					commandIsZeroOrNegetiveOne(quantity, userType);
					System.out.println();
				}
				if(quantity == 0) {
					orderId = quantity;
					break;
				};
			}
			
			String msg = "";
			switch(action){
				case "delete": 
					msg = ordersController_.deleteOrder(orderId);
					break; 
				case "cancel":
					msg = ordersController_.cancelOrder(orderId);
					break;
				case "edit":
					msg = ordersController_.editOrder(orderId, quantity);
					break;	
				default:
					msg = ordersController_.changeOrderStatus(orderId, action);
					break;
			}
			System.out.println();
			if(msg.contains("approved") && !msg.contains("!") )
				msg +=" \nStore inventory updated.";
			System.out.println(msg);
			
			if(msg.contains("successfully")) {
				timesToLoop--;
				System.out.println(viewFunctions_.getSeperator());
				System.out.println();
			}
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
	
	private int validateOrder(int orderId) throws IOException {
		if(orderId != 0 && orderId != -1) {
			Scanner scanner = new Scanner(System.in);
			while (!ordersController_.isOrderExists(orderId) && (orderId != 0 && orderId != -1)) {				
				System.out.println(); // enter
				System.out.println("! Order does not exists. Please try again.");
				System.out.print("Order id: ");	
				orderId = scanner.nextInt();
				scanner.nextLine(); //ignore enter char
			}
		}
		return orderId;
	}
}
