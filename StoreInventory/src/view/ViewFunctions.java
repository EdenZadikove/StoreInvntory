package view;

import java.util.Scanner;

public class ViewFunctions {
	/*Functions:
	 * showProgressBar()
	 * validateInsertedData()
	 * showMenuAgain()
	 * */
	protected String prevScreens = "Main Menu";
	protected String seperator = "--------------------------------------------------------------------------------------------";
	protected String instructionsHeader = "----------------------------------------INSTRUCTIONS----------------------------------------";
	
	public ViewFunctions() {}
	
	protected void showProgressBar(String prevScreens, String currentScreen) {
		final int PROGRESS_BAR_LENGTH = seperator.length() -1;
		String fullProgreeBar = "|  Progress bar: " + prevScreens +  " -----> " + currentScreen;
		int fullProgreeBarSize = fullProgreeBar.length();
		int lengthDeff = PROGRESS_BAR_LENGTH - fullProgreeBarSize;
		
		for(int i = 0; i < lengthDeff ; i++) {
			fullProgreeBar = fullProgreeBar + " ";
		}
		fullProgreeBar = fullProgreeBar + "|\n";
		System.out.println(fullProgreeBar);
	}
	
	
	protected int validateInsertedData(int start, int end, int command, String text, String errorMsg) {
		/* If 0 --->  don't do anything
		 * If -1 --->  don't do anything
		 *  */		
		Scanner scanner = new Scanner(System.in);
		while ((command != 0 && command != -1) &&  !(command <= end && command >= start)) {
			System.out.println(); // print enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		System.out.println(); // print enter
		return command;
	}
	
	
	protected double validateInsertedData(int start, int end, double command, String text, String errorMsg) {
		/* If 0 --->  don't do anything
		 * If -1 --->  don't do anything
		 *  */		
		Scanner scanner = new Scanner(System.in);
		while ((command != 0 && command != -1) &&  !(command <= end && command >= start)) {
			System.out.println(); // enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		return command;
	}
	
	protected int validateInsertedData_noZeroOne(int start, int end, int command, String text, String errorMsg) {		
		Scanner scanner = new Scanner(System.in);
		while (!(command <= end && command >= start)) {
			System.out.println(); // enter
			System.out.println(errorMsg);
			System.out.print(text);	
			command = scanner.nextInt();
			scanner.nextLine(); //ignore enter char
		}
		return command;
	}
	
	
	
	//showMenuAgain
	protected boolean anotherActions() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Would you like to take another action?\n");
		System.out.println("Yes, show me the menu please    ========> 1");
		System.out.println("No, I'm done                    ========> 0\n");
		System.out.print("My choice: ");

		int command = scanner.nextInt();
		scanner.nextLine(); //ignore enter char
		
		command = validateInsertedData_noZeroOne(0, 1, command, "My choice: ", "! Invalid choice. Please try again"); 
		return command == 1; //true - show menu again
	}
	
	public String getPrevScreens() {
		return prevScreens;
	}

	public String getSeperator() {
		return seperator;
	}

	public String getInstructionsHeader() {
		return instructionsHeader;
	}
	
}
