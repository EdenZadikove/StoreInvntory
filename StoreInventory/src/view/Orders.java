package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import controller.OrdersController;

public abstract class Orders{

	protected OrdersController ordersController_;
	protected String prevScreens = "Main Menu";

	public Orders () throws IOException {
		super();
		ordersController_ = new OrdersController();
	}
	
	protected int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		Scanner scanner = new Scanner(System.in);
		while (!(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		System.out.println(); // print enter
		return command;
	}
	
	protected void showProgressBar(String prevScreens, String currentScreen) {
		System.out.println("|  Progress bar: " + prevScreens + " " + "----->" + " " + currentScreen + "             |\n");

	}
	
	protected void showOrdersTable(int isFiltered, String tableHeader, String emptyTableHeader) throws IOException {
		ArrayList<String> ordersList = new ArrayList<String>();
	
		ordersList = ordersController_.showOrdersTable(isFiltered);
		
		if(ordersList.size() == 0) System.out.println(emptyTableHeader);
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
	
	protected boolean showMenuAgain() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nWould you like to take another action?");
		System.out.println("Yes, show me the menu please    ========> 1");
		System.out.println("No, I'm done                    ========> 0");
		System.out.print("My choice: ");

		int command = scanner.nextInt();
		scanner.nextLine(); //ignore enter char
		
		command = validateInsertedData(0, 1, command, "My choice: ", "! Invalid choice. Please try again"); 
		return command == 1;
	}
	
	
	protected int actions(String introduction, String action, int isFilterd) throws IOException {
		Scanner scanner = new Scanner(System.in);
		int validCommandFlag = 0;
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		
		System.out.println("\n----------------------------------------INSTRUCTIONS----------------------------------------");
		System.out.println(introduction);
		System.out.println("\n--------------------------------------------------------------------------------------------");
		
		while(validCommandFlag == 0) {
			
			System.out.println("\nWhich item would you like to " + action +"?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			if(orderId == 0) {
				System.out.println("Going back to Orders Manager Menu...\n");
				break;
			} if(orderId == -1) {
				showOrdersTable(0, "\nOrders Table:\n", "\nNo Orders\n");
				System.out.println("\nWhich item would you like to " + action +"?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
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
					System.out.println("Update order quantity to: ");
					int quantity = scanner.nextInt();
					scanner.nextLine();
					quantity = validateInsertedData(1, 100, quantity, "Update order quantity to: ", "! Quantity must be between 1 to 100");
					msg = ordersController_.editOrder(orderId, quantity);
					break;	
				case "approve":
					
					break;
				case "denied":
					break;
			}
			System.out.println(msg);
			if(msg.contains("successfully")) {
				validCommandFlag = 1;
			}
		}
		//end while
		return orderId;
	}
	
}
