package view;

import java.io.IOException;
import java.util.Scanner;
import controller.Controller;

public class SupplierMenu {
	@SuppressWarnings("resource")
	private OrdersSupplier ordersSupplier_;
	private Controller controller_;
	private ViewFunctions viewFunctions_;
	
	public SupplierMenu() throws IOException {
		ordersSupplier_ = new OrdersSupplier();
		controller_ = new Controller();
		viewFunctions_ = ViewFunctions.getInstance();
	}
	
	public void menuManager() throws IOException {
		int command = 0;
		int showMainMenuAgain = -1; //-1 - show main menu
		while(showMainMenuAgain == -1) {
			command =  mainMenu();
			showMainMenuAgain = showSelectedScreen(command);
		}
		if(showMainMenuAgain == 0 && command != 0) //command arrived from other screen
			showMainMenuAgain = showSelectedScreen(0);
	}
	
	private int mainMenu() {
		Scanner scanner = new Scanner(System.in);
		int command = -1;

		System.out.println("\n" +  viewFunctions_.getMainMenuHeader() +"\n");
		System.out.println(viewFunctions_.showProgressBar("", "Main Menu") + "\n");
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
		int showMainMenuAgain = 0; //0- don't show again, -1- show again
		switch(command) {
		case 0:
			System.out.println("Logout...");
			controller_.logout();
			break;
		case 1:
			showMainMenuAgain = ordersSupplier_.showMenu();
			break;
		}
		return showMainMenuAgain;
	}

}
