package view;

import java.io.IOException;
import java.util.Scanner;

import controller.StoreController;

public class Store {
	private StoreController storeController_;
	public Store() {
		storeController_ = new StoreController();
	}
	
	@SuppressWarnings("resource")
	public void showMenu() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int command = -1;
		int validCommandFlag = 0;
		
		while(validCommandFlag == 0) {
			System.out.println("\nWhich action would you like to do?\n");
			System.out.println("Create a new order     ========> 1");
			System.out.println("Cancel existing order  ========> 2");
			System.out.println("Back to main menu  	   ========> 3");
			System.out.println("Exit            	   ========> 0");
			System.out.println();
			System.out.print("I want to: ");
			
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			if(command > 3 || command < 0) System.out.println("Invalid Choice");
			else validCommandFlag = 1;
		}
		
	}
}
