package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import controller.OrdersController;

public class Orders{

	/* view orders - done
	 * create order - done
	 * cancel pending order - done
	 * delete order
	 * update order*/
	
	private OrdersController ordersController_;
	
	public Orders () throws IOException {
		ordersController_ = new OrdersController();
	}
	
	@SuppressWarnings("resource")
	public int showMenu() throws IOException {
		ShowOrdersTable();
		
		Scanner scanner = new Scanner(System.in);
		int command = -1;
		int validCommandFlag = 0;
		
		while(validCommandFlag == 0) {
			System.out.println("\nWhich action would you like to take?\n");
			System.out.println("Create a new order     ========> 1");
			System.out.println("Cancel pending order   ========> 2");
			System.out.println("Back to main menu      ========> 3");
			System.out.println("Exit the program       ========> 0");
			System.out.println();
			System.out.print("I want to: ");
			
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			if(!validateInsertedData(0,3,command)) System.out.println("Invalid Choice");
			else {
				actionNavigate(command);
				//check if user want to exit the screen
				if(command == 0 || command == 3) {
					validCommandFlag = 1;
					break; // exit the program. go back to "Welcome" view. 
				}
				
				System.out.println("\nWould you like to take another action?");
				System.out.println("Yes             ========> 1");
				System.out.println("No, I'm done    ========> 0");
				System.out.print("My choice: ");
				
				command = scanner.nextInt();
				scanner.nextLine(); //ignore enter char
				
				while(!(validateInsertedData(0, 1, command))) {
					System.out.println("Invalid choice!");
					System.out.print("My choice: ");
					
					command = scanner.nextInt();
					scanner.nextLine(); //ignore enter char
				}
				
				if(command == 0) {
					actionNavigate(command);
					validCommandFlag = 1;
				}
			}
		}
		return command;
	}

	
	private boolean validateInsertedData(int start, int end, int command) {
		return (command <= end && start >= 0);
	}
	
	private void actionNavigate(int command) throws IOException {
		switch(command) {
		case 0:
			ordersController_.saveToFile();
			break;
		case 1:
			int OrderId = createOrder();
			System.out.println("Order successfully created!");
			System.out.println("Order id: " + OrderId);
			ShowOrdersTable();
			break;
		case 2:
			cancelOrder();
			break;
		case 3:
			ordersController_.saveToFile();
			break;
		}
	}
	
	private void ShowOrdersTable() throws IOException {
		ArrayList<String> ordersList = new ArrayList<String>();
		
		ordersList = ordersController_.showOrdersTable();
		
		System.out.println("\nOrders Table:");
		System.out.println("| Order ID            | Item Name            | Quantity             | Order Status         |");
		
		for(String order : ordersList) {
			String OrderId = StringUtils.substringBetween(order, "OrderId:", ";");
			String ItemName = StringUtils.substringBetween(order, "ItemName:", ";");
			String Quantity = StringUtils.substringBetween(order, "Quantity:", ";");
			String OrderStatus = StringUtils.substringBetween(order, "OrderStatus:", ";");
			System.out.printf("| %-20s| %-20s | %-20s | %-20s |\n",OrderId, ItemName, Quantity, OrderStatus);
		}
		
	}
	
	private int createOrder() throws IOException {
		Scanner scanner = new Scanner(System.in);
		String items[] = {"Short-pants", "T-shirt", "Swimsuit", "Coat", "Gloves", "Scarf"} ;
		int item = -1;
		int quantity = 0;
		int validCommandFlag = 0; 
		
		System.out.println("\nWhich item would you like to order?\n");
		System.out.println("Short-pants  ========>  1");
		System.out.println("T-shirt      ========>  2");
		System.out.println("Swimsuit     ========>  3");
		System.out.println("Coat         ========>  4");
		System.out.println("Gloves       ========>  5");
		System.out.println("Scarf        ========>  6");
		
		while(validCommandFlag == 0) {
			
			System.out.println("I would like to order: ");
			item = scanner.nextInt();
			scanner.nextLine(); 
			
			if(item >= 1 && item <= 6) validCommandFlag = 1;
			else System.out.println("Invalid item!");
		}
		
		validCommandFlag = 0; //reset flag
		System.out.print("How many items would you like to order? ");
		while(validCommandFlag == 0) {
			quantity = scanner.nextInt();
			scanner.nextLine(); 
			
			if(quantity > 0) validCommandFlag = 1;
			else System.out.println("Quantity must be more then 0");
		}
		int orderId = ordersController_.createOrder(items[item-1], quantity);
		
		return orderId;	
	}
	
	@SuppressWarnings("resource")
	private void cancelOrder() {
		Scanner scanner = new Scanner(System.in);
		int validCommandFlag = 0;
		System.out.println("\nPay attention- you can only cancel orders with status 'pending'.");
		while(validCommandFlag == 0) {
			
			System.out.println("Which item would you like to cancel?\n");
			System.out.print("Order id: ");
			
			int orderId = scanner.nextInt();
			scanner.nextLine();
			
			String msg = ordersController_.cancelOrder(orderId);
			System.out.println(msg);
			if(msg.contains("successfully")) {
				validCommandFlag = 1;
			}	
		}
	}

	


}
