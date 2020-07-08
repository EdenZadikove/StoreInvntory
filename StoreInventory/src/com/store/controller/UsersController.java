package com.store.controller;

import java.util.ArrayList;

import com.store.model.services.UsersService;

public class UsersController {
	private UsersService usersService_;
	
	public UsersController() {
		usersService_ = new UsersService();
	}
	
	public ArrayList<String> getAllUsers(){
		return usersService_.getAllUsers();
	}
	
	public boolean createNewUser(String firstName, String lastName, String password, String email, String userType) {
		if(isEmpty(firstName) || isEmpty(lastName) || isEmpty(password) || isEmpty(email) || isEmpty(userType))
			throw new IllegalArgumentException("! One or more of the inputs is empty. Please try again.");
		if(!isValidEmail(email))
			throw new IllegalArgumentException("! Email is not valid. Please try again.");
		
		//add validation
		int userTypeNumber;
		switch(userType) {
		case "Admin":
			userTypeNumber = 1;
			break;
		case "Seller":
			userTypeNumber = 2;
			break;
		case "Supplier":
			userTypeNumber = 3;
			break;
		default:
			throw new IllegalArgumentException("! Invalid role. Please try again.");
		}
		return usersService_.createNewUser(firstName + "_" + lastName, password, email, userTypeNumber);
	}
	
	public boolean deleteUser(String email) throws Exception{
		if(isEmpty(email))
			throw new IllegalArgumentException("! Email must not be empty. Please try again.");
		if(!isValidEmail(email))
			throw new IllegalArgumentException("! Email is not valid. Please try again.");
		return usersService_.deleteUser(email);
	}
	
	public String getUserAsString(String email) {
		if(isEmpty(email))
			throw new IllegalArgumentException("! Emaill must not be empty. Please try again.");
		if(!isValidEmail(email))
			throw new IllegalArgumentException("! Email is not valid. Please try again.");
		return usersService_.getUserAsString(email);
	}
	
	public void saveToFileUsers() {
		usersService_.saveToFileUsers();
	}
	
	private boolean isEmpty(String str) {
		boolean res = (str.equals("") || str.equals(null));
		return res;
	}
	
	private boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
}
