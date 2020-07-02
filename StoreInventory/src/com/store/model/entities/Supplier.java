package com.store.model.entities;

public class Supplier extends User {
	
	public Supplier(String userName, int passwrod, String email) {
		super(userName, passwrod, email, 3);
	}

	@Override
	public String toString() {
		return userName + "::" + password + "::" + email + "::" + userType;
	}
}
