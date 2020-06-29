package com.store.model;

import java.io.IOException;

import com.store.model.entities.Admin;
import com.store.model.entities.Seller;
import com.store.model.entities.Supplier;
import com.store.model.entities.User;

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
