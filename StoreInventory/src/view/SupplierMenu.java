package view;

import java.io.IOException;
import java.util.Scanner;

import controller.Controller;

public class SupplierMenu {
	
	@SuppressWarnings("resource")
	private Controller controller_ = null;
	
	public SupplierMenu() {
		controller_ = new Controller();
	}
	
	public void menuManager() throws IOException {
		int command;
		int showMainMenuAgain = -1; //-1 - show main menu
		while(showMainMenuAgain == -1) {
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);
			if(showMainMenuAgain == 0) showSelectedScreen(0);
		}
	}
	
	private int mainMenu() {
		Scanner scanner = new Scanner(System.in);
		int command = -1;

		System.out.println("\n-----------------------------------------Main Menu------------------------------------------\n");
		System.out.println("|  Progress bar:  Main Menu                                                                 |\n");
		System.out.println("Which screen would you like to get?\n");
		
		
		System.out.println("Orders Manager ========> 1");
		System.out.println("Logout         ========> 0");
		
		System.out.println();
		System.out.print("I want to view: ");
		
		command = scanner.nextInt();
		scanner.nextLine(); //ignore enter char
		
		command = validateInsertedData(0, 3, command, "I want to view: " ,"! Invalid choice. Please try again. ");
		return command;
	}
	
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
	
	private int showSelectedScreen(int command) throws IOException {
		int showMainMenuAgain = 0;
		switch(command) {
		case 0:
			System.out.println("Logout...\n");
			controller_.logout();
			break;
		case 1:
			OrdersSupplier ordersSupplier = new OrdersSupplier();
			ordersSupplier.showMenu();
			break;
		}
		return showMainMenuAgain;
	}

}
