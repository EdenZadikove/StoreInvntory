package view;

import java.io.IOException;
import java.util.Scanner;

public class Welcome {
	
	private Login login_ = new Login();
	private ViewFunctions viewFunctions_;
		
	public Welcome() {
		viewFunctions_ = ViewFunctions.getInstance();
	}
	
	public void welcomeMenu() throws IOException {	
		Scanner scanner = new Scanner(System.in);
		int command;
		int userType = 0;
		boolean showWelcomeMenuAgain = true;
		
		while(showWelcomeMenuAgain) {
			System.out.println("\n" + viewFunctions_.getWelcomMenuHeader() + "\n");
			System.out.println("Login ========> 1");
			System.out.println("Exit  ========> 0");
			System.out.println(); //enter
			System.out.print("I want to: ");
			
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
			
			viewFunctions_.validateInsertedData(0, 1, command, "I want to: ", "! Invalid choice. Please try again.");
			if(command == 0) //user want to exit
				showWelcomeMenuAgain = false;
			else {
				userType = login_.signIn(); //do login
				if(userType == 0) showWelcomeMenuAgain = false; //3 wrong login attempts. exit the system
				nevigateMenu(userType); //returned from user menu
				userType = 0; //reset next session
			}
		}
		nevigateMenu(userType); //exit success
	}
	
	private void nevigateMenu(int userType) throws IOException {
		switch(userType) {
		case 1:
			AdminMenu adminMenu = new AdminMenu();
			adminMenu.menuManager();
			break;
		case 2:
			SellerMenu sellerMenu = new SellerMenu();
			sellerMenu.menuManager();
			break;
		case 3:
			SupplierMenu supplierMenu = new SupplierMenu();
			supplierMenu.menuManager();
			break;
		default:
			exit(0);
			break;
		}
		//we are here after user did logout
	}
	
	
	private void exit(int status) throws IOException {
		System.out.println();
		System.out.println("Exit the program...\nBye Bye");
		System.exit(status); 
	}
	
	private void clearConsole() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}

