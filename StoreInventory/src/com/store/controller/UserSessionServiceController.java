package com.store.controller;

import com.store.model.services.UserSessionService;

public class UserSessionServiceController {

	private UserSessionService userSessionService_; 
	
	public UserSessionServiceController(){
		this.userSessionService_ = new UserSessionService();
	}
	
	public int login(String email, String password) throws IllegalArgumentException{
		if(email.equals("") || email == null)
			throw new IllegalArgumentException("\n! Email must not be null. Please try again.");
		return userSessionService_.signIn(email, password);
	}
	
	public void logout() {
		userSessionService_.logout();
	}
	
	public String getUserName() {
		return userSessionService_.getUserName();
	}
}
