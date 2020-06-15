package view;

import java.io.IOException;
import java.util.Scanner;

public class AdminMenu {
	
	public AdminMenu() {}
	
	@SuppressWarnings("resource")
	public void menuManager() throws IOException {
		int command;
		int showMainMenuAgain = 3; //3- show main menu
		while(showMainMenuAgain == 3) {
			command =  mainMenu();
			showSelectedScreen(command);
		}
	}
	
	
	
	
	public int mainMenu() throws IOException {
		Scanner scanner = new Scanner(System.in);
		int command = -1;
		int validCommandFlag = 0;
		System.out.println("Main Menu");
		while(validCommandFlag == 0) {
			System.out.println("Store Manager  ========> 1");
			System.out.println("Orders Manager ========> 2");
			System.out.println("Users Manager  ========> 3");
			System.out.println("Exit           ========> 0");
			
			System.out.println();
			System.out.print("I want to view: ");
			
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			if(command > 3 || command < 0) System.out.println("Invalid Choice");
			else validCommandFlag = 1;
		}
		return command;
	}
	
	
	private int showSelectedScreen(int command) throws IOException {
		int showMainMenuAgain = 0;
		switch(command) {
		case 0:
			break;
		case 1:
			Store store = new Store();
			store.showMenu();
			break;
		case 2:
			Orders orders = new Orders();
			showMainMenuAgain = orders.showMenu();
			break;
		
		case 3:
			Users users = new Users();
			users.showMenu();
			break;
		}
		return showMainMenuAgain;
		
	}
}
