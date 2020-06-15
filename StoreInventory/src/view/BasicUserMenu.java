package view;

import java.util.Scanner;

public class BasicUserMenu {
	
	@SuppressWarnings("resource")
	public int menu() {
		
		Scanner scanner = new Scanner(System.in);
		int command = -1;
		int validCommandFlag = 0;
		
		while(validCommandFlag == 0) {
			System.out.println("Store Manager  ========> 1");
			System.out.println("Exit           ========> 0");
		
			command = scanner.nextInt();
			scanner.nextLine();
			
			if(command != 0 && command != 1) System.out.println("Invalid Choice");
			else validCommandFlag = 1;
		}
		return command;
	}
}
