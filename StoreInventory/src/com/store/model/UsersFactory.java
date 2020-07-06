package com.store.model;

import com.store.model.entities.Admin;
import com.store.model.entities.Seller;
import com.store.model.entities.Supplier;
import com.store.model.entities.User;

public class UsersFactory {

	public User createUser(String userName, String password, String email, int userType, int employeeNumber, String storeName){
		User user = null;
		
		switch(userType) {
		case 1: 
			user = new Admin(userName, password, email.toLowerCase(), employeeNumber ,storeName);
			break;
		case 2:
			user = new Seller(userName, password, email.toLowerCase(), employeeNumber ,storeName);
			break;
		case 3:
			user = new Supplier(userName, password, email.toLowerCase());
			break;
		}
		return user;
	}
}
