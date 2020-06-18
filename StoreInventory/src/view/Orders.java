package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import controller.OrdersController;
import model.Model;

public abstract class Orders{
	protected ViewFunctions viewFunctions; 
	protected OrdersController ordersController_;
	protected ArrayList<String> ordersList = new ArrayList<String>();
	protected String screenHeader = "------------------------------------Orders Manager Menu-------------------------------------";
	
	public Orders () throws IOException {
		super();
		viewFunctions = new ViewFunctions();
		ordersController_ = new OrdersController();
		
	}
	
	
	protected void commandIsZeroOrNegetiveOne(int command, int userType) throws IOException{
		String prevScreensTemp = viewFunctions.getPrevScreens() + " -----> " + "Orders Manager Menu";
		
		if(command == 0) {
			System.out.println("Going back to Orders Manager Menu...\n");
		} 
		else if(command == -1) {
			switch(userType) {
			case 1: //admin
				System.out.println();
				System.out.println(viewFunctions.getSeperator());
				System.out.println();
				viewFunctions.showProgressBar(prevScreensTemp, "View pending orders table" );
				
				showOrdersTable(0, "Orders Table:\n", "No orders\n");
				System.out.println(viewFunctions.getSeperator());
				
				break;
			case 3: //supplier
				System.out.println();
				System.out.println(viewFunctions.getSeperator());
				System.out.println();
				viewFunctions.showProgressBar(prevScreensTemp, "View pending orders table" );
				showOrdersTable(1, "\nPending orders Table:\n", "\nNo pending orders\n");
				System.out.println();
			}
		}
	}
	

	protected void showOrdersTable(int isFiltered, String tableHeader, String emptyTableHeader) throws IOException {
	
		ordersList = ordersController_.showOrdersTable(isFiltered);
		
		if(ordersList.size() == 0) {
			System.out.println(emptyTableHeader);
		} else {
			System.out.println(tableHeader);
			System.out.println("| Order ID            | Item Name            | Quantity             | Order Status         |");
			
			for(String order : ordersList) {
				String OrderId = StringUtils.substringBetween(order, "OrderId:", ";");
				String ItemName = StringUtils.substringBetween(order, "ItemName:", ";");
				String Quantity = StringUtils.substringBetween(order, "Quantity:", ";");
				String OrderStatus = StringUtils.substringBetween(order, "OrderStatus:", ";");
				System.out.printf("| %-20s| %-20s | %-20s | %-20s |\n",OrderId, ItemName, Quantity, OrderStatus);
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
		System.out.println(viewFunctions.getInstructionsHeader());
		System.out.println(introduction);
		System.out.println(viewFunctions.getSeperator());
		
		
		if(itemsCounter != 1) {
			//If itemsCounter == 1, then no need to ask how many times I want to do the action
			while (timesToLoop == -1) {
				String str = "\nHow many items do you want to " + action + "?";
				System.out.println(str);
				timesToLoop = scanner.nextInt();
				timesToLoop = viewFunctions.validateInsertedData(1, itemsCounter, timesToLoop, "", "! Items range is: 1 to " + itemsCounter);
				commandIsZeroOrNegetiveOne(timesToLoop, userType);
			}
		}

		while(timesToLoop > 0 ) {
			
			System.out.println("\nWhich item would you like to " + action +"?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			commandIsZeroOrNegetiveOne(orderId, userType);
			
			while(orderId == -1) {
				System.out.println("\nWhich item would you like to " + action +"?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
				commandIsZeroOrNegetiveOne(orderId, userType);
			}
			if(orderId == 0) {
				break;
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
					System.out.print("Update order quantity to: ");
					int quantity = scanner.nextInt();
					scanner.nextLine();
					quantity = viewFunctions.validateInsertedData(1, 100, quantity, "Update order quantity to: ", "! Quantity must be between 1 to 100");
					msg = ordersController_.editOrder(orderId, quantity);
					break;	
				default:
					msg = ordersController_.changeOrderStatus(orderId, action);
					break;
			}
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
		return orderId;
	}
	
	
	protected boolean isEmptyMap(String filter) {
		if(ordersController_.itemsCounterByFilter(filter) == 0) { //empty orders map
			System.out.println("Action can not be taken. No " + filter + " orders in the table.");
			System.out.println();
			System.out.println(viewFunctions.getSeperator());
			return true;
		}
		return false;
	}
}
