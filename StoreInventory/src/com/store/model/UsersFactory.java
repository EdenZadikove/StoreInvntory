package com.store.model;

import java.io.IOException;

public class UsersFactory {

	public User getUser(User user) throws IOException {
		int userType = user.getUserType();
		String userName = user.getUserName();
		int password = user.getPassword();
		String email = user.getEmail();
		String phoneNumber = user.getPhoneNumber();
		
		switch(userType) {
		case 1: 
			user = new Admin(userName, password, email, phoneNumber);
			break;
		case 2:
			user = new Seller(userName, password, email, phoneNumber);
			break;
		case 3:
			user = new Supplier(userName, password, email, phoneNumber);
			break;
		}
		return user;
	}
}
