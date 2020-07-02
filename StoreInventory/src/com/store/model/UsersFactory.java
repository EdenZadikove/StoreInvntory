package com.store.model;

import com.store.model.entities.Admin;
import com.store.model.entities.Seller;
import com.store.model.entities.Supplier;
import com.store.model.entities.User;

public class UsersFactory {

	public User getUser(User user){
		int userType = user.getUserType();
		String userName = user.getUserName();
		int password = user.getPassword();
		String email = user.getEmail();
		
		switch(userType) {
		case 1: 
			user = new Admin(userName, password, email, ((Admin)user).getEmployeeNumber() ,((Admin)user).getStoreName());
			break;
		case 2:
			user = new Seller(userName, password, email, ((Seller)user).getEmployeeNumber(), ((Seller)user).getStoreName());
			break;
		case 3:
			user = new Supplier(userName, password, email);
			break;
		}
		return user;
	}
}
