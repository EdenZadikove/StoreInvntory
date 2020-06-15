package view;
import java.io.IOException;
import java.util.Scanner;

import model.Model;

public class Welcome {
	
	private Login login = new Login();
	private Model model_ = new Model();
	
	@SuppressWarnings("resource")
	public void welcome() throws IOException {
	
		Scanner scanner = new Scanner(System.in);
		int command;
		int userType = 0;
		
		System.out.println("\n-----------------------------Welcome To Store Inventory System!-----------------------------\n");
		System.out.println("Login ========> 1");
		System.out.println("Exit  ========> 0");
		System.out.print("I want to: ");
		
		command = scanner.nextInt();
		scanner.nextLine(); //ignore enter char
		
		userType = afterLogin(command, login);
		if(userType == 0) exit(1);
		else  command = nevigateMenu(userType);

	}
	
	private int afterLogin(int command, Login login) throws IOException {	
		int userType = 0;	
		switch(command) {
		case 1: 
			userType = login.signIn();
			break;
		case 0:
			exit(0);
			break;
		}
		return userType;
	}
	
	private int nevigateMenu(int userType) throws IOException {
		int command = 0;
		
		

		switch(userType) {
		case 1:
			AdminMenu adminMenu = new AdminMenu();
			adminMenu.menuManager();
			exit(0);
			break;
		case 2:
			BasicUserMenu basicUserMenu = new BasicUserMenu();
			command = basicUserMenu.menu();
			break;
		case 3:
			SupplierMenu supplierMenu = new SupplierMenu();
			command = supplierMenu.menu();
			break;
		}
		return command;
	}
	
	
	private void exit(int status) throws IOException {
		//model_.saveToFile_orders();
		System.out.println("Bye Bye");
		System.exit(status); //exit with fail
		
	}
	
	private void clearConsole() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}

