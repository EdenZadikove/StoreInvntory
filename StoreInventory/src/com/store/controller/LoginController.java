package com.store.controller;

import java.io.IOException;

import com.store.model.Model;

public class LoginController {
	
	//private LoginRepository loginRepo_; //model object
	private Model model_;
	
	public LoginController()  {
		this.model_ = new Model();
	}
	
	public int login(String email, int password) throws IOException {
		//check data from view
		if(email == "" || email == null) {
			throw new IllegalArgumentException("Email or password must be not null");
		}
		
		return model_.signIn(email, password);
	}
}
