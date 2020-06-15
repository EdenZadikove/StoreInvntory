package controller;

import model.Model;

public class UsersController {

	private Model model_;
	public UsersController() {
		model_ = new Model();
	}
	
	public String getUserName() {
		return model_.getUserName();
	}
	
}
