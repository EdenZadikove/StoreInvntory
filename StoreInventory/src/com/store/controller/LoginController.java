package com.store.controller;

import java.io.IOException;
import com.store.model.Model;

public class LoginController {
	private Model model_;
	
	public LoginController()  {
		this.model_ = new Model();
	}
	
	public int login(String email, int password) throws IOException {
		if(email.equals("") || email == null)
			throw new IllegalArgumentException("! Email must be not null. Please tru again.");
		return model_.signIn(email, password);
	}
	
	public void logout() {
		model_.logout();
	}
}
