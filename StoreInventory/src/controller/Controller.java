package controller;

import model.Model;


public class Controller {
	private Model model_ = null;
	
	public Controller() {
		 model_ = new Model(); 
	}
	
	public void logout() {
		model_.logout();
	}
	

}
