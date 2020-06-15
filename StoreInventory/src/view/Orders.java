package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import controller.OrdersController;

public class Orders{

	/* view orders - done
	 * create order - done
	 * cancel pending order - done
	 * delete order - in process
	 * update order*/
	
	private OrdersController ordersController_;
	private String prevScreens = "Main Menu";
	public Orders () throws IOException {
		ordersController_ = new OrdersController();
	}
	
	@SuppressWarnings("resource")
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
			
			command = validateInsertedData(-1,4,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showOrdersManagerMenu_again = actionNavigate(command); //command choice from Orders Manager Menu
			
			if(!showOrdersManagerMenu_again) {  //showOrdersManagerMenu_again == false
				System.out.println("Exit the program...\n");
				actionNavigate(0); //exit the program
				validCommandFlag = 1; //end loop
			}
		}
		//end while
		return command;
	}

	
	
	@SuppressWarnings("resource")
	private int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
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
	
	public void showProgressBar(String prevScreens, String currentScreen) {
		System.out.println("|  Progress bar: " + prevScreens + " " + "----->" + " " + currentScreen + "             |\n");

	}
	
	
	private void showOrdersManagerMenu() {
		String screenHeader = "\n------------------------------------Orders Manager Menu-------------------------------------\n";
		System.out.println(screenHeader);
		showProgressBar(prevScreens, "Order Manager Menu");
		
		System.out.println("Which action would you like to take?\n");

		System.out.println("View orders table      ========> 1");
		System.out.println("Create a new order     ========> 2");
		System.out.println("Cancel pending order   ========> 3");
		System.out.println("Delete existing order  ========> 4");			
		System.out.println("Edit pending order     ========> 5");
		
		System.out.println("Back to main menu      ========> -1");
		System.out.println("Exit the program       ========> 0");
	}
	
	
	
	private boolean actionNavigate(int command) throws IOException {
		boolean result = false; //if result == true then show menu again
		String prevScreensTemp = prevScreens + " -----> " + "Orders Manager Menu";
		
		switch(command) {
		case 0: //Exit
			ordersController_.saveToFile();
			break;
		case 1: //View orders table
			showProgressBar(prevScreensTemp, "View orders table" );
			showOrdersTable();
			result = showMenuAgain();
			break;
		case 2: //Create a new order
			showProgressBar(prevScreensTemp, "Create a new order");
			createOrder(); 
			result = showMenuAgain();
			break;
		case 3: //Cancel pending order 
			showProgressBar(prevScreensTemp, "Cancel pending order");
			int res = cancelOrder(); 
			if(res == 0) result = true;
			else result = showMenuAgain();
			break;
		case 4: //Delete existing order
			showProgressBar(prevScreensTemp, "Delete existing order");
			deleteOrder();
			result = showMenuAgain();
			break;
		case 5: //Edit pending order
			showProgressBar(prevScreensTemp, "Edit pending order");
			editOrder();
			result = showMenuAgain();
			break;
		case -1: //Back to main menu
			System.out.println("Going back to Main Menu...\n");
			ordersController_.saveToFile();
			break;
		}
		return result;
	}
	
	
	private boolean showMenuAgain() {
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
	
	private void showOrdersTable() throws IOException {
		ArrayList<String> ordersList = new ArrayList<String>();
		
		ordersList = ordersController_.showOrdersTable();
		
		System.out.println("\nOrders Table:\n");
		System.out.println("| Order ID            | Item Name            | Quantity             | Order Status         |");
		
		for(String order : ordersList) {
			String OrderId = StringUtils.substringBetween(order, "OrderId:", ";");
			String ItemName = StringUtils.substringBetween(order, "ItemName:", ";");
			String Quantity = StringUtils.substringBetween(order, "Quantity:", ";");
			String OrderStatus = StringUtils.substringBetween(order, "OrderStatus:", ";");
			System.out.printf("| %-20s| %-20s | %-20s | %-20s |\n",OrderId, ItemName, Quantity, OrderStatus);
		}
		
	}
	
	
	
	private void createOrder() throws IOException {
		Scanner scanner = new Scanner(System.in);
		String items[] = {"Short-pants", "T-shirt", "Swimsuit", "Coat", "Gloves", "Scarf"} ;
		int item = -1;
		int quantity = 0;
		
		System.out.println("\nWhich item would you like to order?\n");
		showItemsMenu();
		
		System.out.println("\nI would like to order: ");
		item = scanner.nextInt();
		scanner.nextLine();
		
		//check if user chose a valid option.
		item = validateInsertedData(1, 6, item, "I would like to order: ", "! Invalid item. Please try again"); 
		
		System.out.print("How many items would you like to order? ");
		quantity = scanner.nextInt();
		scanner.nextLine();
		
		//check if user chose a valid option.
		quantity = validateInsertedData(1, 100, quantity, "I would like to order: ", "! Quantity must be between 1 to 100"); 
		
		int orderId = ordersController_.createOrder(items[item-1], quantity);
		
		System.out.println("Order successfully created!");
		System.out.println("Order id: " + orderId);
	}
	
	private void showItemsMenu() {
		
		System.out.println("Short-pants  ========>  1");
		System.out.println("T-shirt      ========>  2");
		System.out.println("Swimsuit     ========>  3");
		System.out.println("Coat         ========>  4");
		System.out.println("Gloves       ========>  5");
		System.out.println("Scarf        ========>  6");
	}
	
	
	
	@SuppressWarnings("resource")
	private int cancelOrder() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int validCommandFlag = 0;
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		
		System.out.println("\n----------------------------------------INSTRUCTIONS----------------------------------------");
		System.out.println("\n1.  Pay attention- you can only cancel orders with status 'pending'");
		System.out.println("\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu");
		System.out.println("\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID");
		System.out.println("\n--------------------------------------------------------------------------------------------");
		
		while(validCommandFlag == 0) {
			
			System.out.println("\nWhich item would you like to cancel?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			if(orderId == 0) {
				System.out.println("Going back to Orders Manager Menu...\n");
				break;
			}
			if(orderId == -1) {
				showOrdersTable();
				System.out.println("\nWhich item would you like to cancel?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
			}
			
			
			String msg = ordersController_.cancelOrder(orderId);
			System.out.println(msg);
			if(msg.contains("successfully")) {
				validCommandFlag = 1;
			}	
		}
		//end while
		return orderId;
	}
	
	
	private void deleteOrder() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int validCommandFlag = 0;
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		
		System.out.println("\n----------------------------------------INSTRUCTIONS----------------------------------------");
		System.out.println("\n1.  Pay attention: - you can only delete orders with status 'denied' OR 'approved'");
		System.out.println("\n                   - By deleting order you won't see it in the table anymore.");
		System.out.println("\n                   - This action can not be undo!");
		System.out.println("\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu");
		System.out.println("\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID");
		System.out.println("\n--------------------------------------------------------------------------------------------");
		
		while(validCommandFlag == 0) {
			
			System.out.println("\nWhich item would you like to delete?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			if(orderId == 0) {
				System.out.println("Going back to Orders Manager Menu...\n");
				break;
			} if(orderId == -1) {
				showOrdersTable();
				System.out.println("\nWhich item would you like to delete?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
			}
			String msg = ordersController_.deleteOrder(orderId);
			System.out.println(msg);
			if(msg.contains("successfully")) {
				validCommandFlag = 1;
			}
		}
		//end while
	}
	
	private void editOrder() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int validCommandFlag = 0;
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		
		System.out.println("\n----------------------------------------INSTRUCTIONS----------------------------------------");
		System.out.println("\n1.  Pay attention: - you can only edit orders with status 'pending'");
		System.out.println("\n                   - This action can not be undo!");
		System.out.println("\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu");
		System.out.println("\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID");
		System.out.println("\n--------------------------------------------------------------------------------------------");
		
		while(validCommandFlag == 0) {
			
			System.out.println("\nWhich item would you like to edit?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			if(orderId == 0) {
				System.out.println("Going back to Orders Manager Menu...\n");
				break;
			} if(orderId == -1) {
				showOrdersTable();
				System.out.println("\nWhich item would you like to delete?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
			}
			String msg = ordersController_.deleteOrder(orderId);
			System.out.println(msg);
			if(msg.contains("successfully")) {
				validCommandFlag = 1;
			}
		}
		//end while
	}
	
	private void actions(String introduction, String action) throws IOException {
		Scanner scanner = new Scanner(System.in);
		int validCommandFlag = 0;
		int orderId = 0; //if orderId == 0----> go back to Orders main menu
		
		System.out.println(introduction);
		while(validCommandFlag == 0) {
			
			System.out.println("\nWhich item would you like to " + action +"?");
			System.out.print("Order id: ");
			
			orderId = scanner.nextInt();
			scanner.nextLine();
			
			if(orderId == 0) {
				System.out.println("Going back to Orders Manager Menu...\n");
				break;
			} if(orderId == -1) {
				showOrdersTable();
				System.out.println("\nWhich item would you like to " + action +"?");
				System.out.print("Order id: ");
				
				orderId = scanner.nextInt();
				scanner.nextLine();
			}
			
			switch(action){
				String msg;
				case "delete": 
					msg = ordersController_.deleteOrder(orderId);
					break; 
				case "cancel"
					
			}
			String msg = ordersController_.deleteOrder(orderId);
			System.out.println(msg);
			if(msg.contains("successfully")) {
				validCommandFlag = 1;
			}
		}
		//end while
	}
	
	
	
	
}
