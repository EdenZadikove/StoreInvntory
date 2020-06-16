package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/* view orders - done
 * create order - done
 * cancel pending order - done
 * delete order - done
 * edit order quantity -  done*/

public class OrdersAdmin extends Orders {

	public OrdersAdmin() throws IOException {
		super();
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
			
			command = validateInsertedData(-1,5,command, "I want to: ", "! Invalid choice!. Please try again"); //check if user chose a valid option.
			showOrdersManagerMenu_again = actionNavigate(command); //command choice from Orders Manager Menu
			
			if(!showOrdersManagerMenu_again) {  //showOrdersManagerMenu_again == false
				if(command == -1) {
					System.out.println("Going back to Main Menu...\n");
				}
				else {
					System.out.println("Exit the program...\n");
					actionNavigate(0); //exit the program
				}
				validCommandFlag = 1; //end loop
			}
		}
		//end while
		return command;
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
		int res = -1;
		switch(command) {
		case 1: //View orders table
			showProgressBar(prevScreensTemp, "View orders table" );
			showOrdersTable(0, "\nOrders Table:\n", "No Orders");
			result = showMenuAgain();
			break;
		case 2: //Create a new order
			showProgressBar(prevScreensTemp, "Create a new order");
			createOrder(); 
			result = showMenuAgain();
			break;
		case 3: //Cancel pending order 
			showProgressBar(prevScreensTemp, "Cancel pending order");
			res = cancelOrder(); 
			if(res == 0) result = true;
			else result = showMenuAgain();
			break;
		case 4: //Delete existing order
			showProgressBar(prevScreensTemp, "Delete existing order");
			res = deleteOrder();
			if(res == 0) result = true;
			else result = showMenuAgain();
			break;
		case 5: //Edit pending order
			showProgressBar(prevScreensTemp, "Edit pending order");
			res = editOrder();
			if(res == 0) result = true;
			else result = showMenuAgain();
			break;
		default: //Exit screen
			ordersController_.saveToFile();
			break;
		}
		return result;
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
		String introduction = "\n1.  Pay attention- you can only cancel orders with status 'pending'\n" + 
							  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu\n" +
							  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID";
							  
		return actions(introduction, "cancel", 0, 1);
	}
	
	
	private int deleteOrder() throws IOException {
		
		String introduction = "\n1.  Pay attention: - you can only delete orders with status 'denied', 'approved' or 'canceled'\n" + 
				  "\n                   - By deleting order you won't see it in the table anymore.\n" +
				  "\n                   - This action can not be undo!\n" + 
				  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu.\n" +
				  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID.\n";
				  
		return actions(introduction, "delete", 0, 1);
	}
	
	private int editOrder() throws IOException {
		
		String introduction = "\n1.  Pay attention: - you can only edit orders with status 'pending'\n" + 
				  "\n                   - This action can not be undo!\n" +
				  "\n2.  Press '0' in any stage if you want to exit and go back to Orders Manager Menu.\n" + 
				  "\n3.  Press '-1' in any stage if you want show orders table in order to choose order ID.";
				  
		return actions(introduction, "edit", 0, 1);
	}
	
	
}
