package com.store.controller;

import com.store.model.Model;

public class UsersController {

	private Model model_;
	public UsersController() {
		model_ = new Model();
	}
	
	public String getUserName() {
		return model_.getUserName();
	}
	
}
