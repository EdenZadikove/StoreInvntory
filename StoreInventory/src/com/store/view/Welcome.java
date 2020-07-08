package com.store.view;

public class Welcome {
	
	private Login login_;
	private ViewFunctions viewFunctions_;
	private MenuFactory menuFactory_;
	public Welcome() {
		viewFunctions_ = new ViewFunctions();
		menuFactory_ = new MenuFactory();
	}

	public void welcomeMenu(){		
		int command = -1; //initialize variable
		int userType = 0;
		boolean showWelcomeMenuAgain = true;
		
		while(showWelcomeMenuAgain) {
			login_ = createNewSession();
			System.out.println("\n" + viewFunctions_.getWelcomMenuHeader() + "\n");
			System.out.println("Login ========> 1");
			System.out.println("Exit  ========> 0");
			System.out.println(); //enter
			command = viewFunctions_.validateIntInput("I want to: ");
			
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
	
	private void nevigateMenu(int userType){
		if(userType == 0)
			exit(1);
		menuFactory_.createMenu(userType).menuManager();
	}
	
	private void exit(int status){
		System.out.println(viewFunctions_.getSeperator() + "\n");
		System.out.println("Exit the program...\nBye Bye");
		System.exit(status); 
	}
	
	private Login createNewSession(){
		return login_ = new Login();
	}
}

