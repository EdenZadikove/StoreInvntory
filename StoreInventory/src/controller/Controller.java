package controller;

import java.util.ArrayList;

import model.Model;


public class Controller {
	private Model model_ = null;
	
	public Controller() {
		 model_ = new Model(); 
	}
	
	public void logout() {
		model_.logout();
	}
	
	public ArrayList<String> getProductDetails(String product) {
		return model_.getProductDetails(product);
	}

}
