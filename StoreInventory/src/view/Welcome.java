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
		boolean showWelcomeMenuAgain = true;
		
		while(showWelcomeMenuAgain) {
			System.out.println("\n-----------------------------Welcome To Store Inventory System!-----------------------------\n");
			System.out.println("Login ========> 1");
			System.out.println("Exit  ========> 0");
			System.out.print("I want to: ");
			
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			command = validateInsertedData(0, 1, command, "I want to: ", "! Invalid choice. Please try again.");
			if(command == 0) //user want to exit
				showWelcomeMenuAgain = false;
			else {
				userType = signIn(login); //do login
				if(userType == 0) showWelcomeMenuAgain = false; //3 wrong login attempts. exit the system
			}
			nevigateMenu(userType); //returned from user menu
			userType = 0; //reset next session
		}
		
		exit(0); //exit success
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
	
	private int signIn(Login login) throws IOException {	
		return login.signIn();
	}
	
	private void nevigateMenu(int userType) throws IOException {
		
		switch(userType) {
		case 1:
			AdminMenu adminMenu = new AdminMenu();
			adminMenu.menuManager();
			break;
		case 2:
			BasicUserMenu basicUserMenu = new BasicUserMenu();
			basicUserMenu.menu();
			break;
		case 3:
			SupplierMenu supplierMenu = new SupplierMenu();
			supplierMenu.menuManager();
			break;
		}
		
		//we are here after user did logout
		
	}
	
	
	private void exit(int status) throws IOException {
		
		System.out.println("Bye Bye");
		System.exit(status); //exit with fail
		
	}
	
	private void clearConsole() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}

