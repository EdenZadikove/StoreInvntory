package com.store.model;

public class Supplier extends User {
	
	public Supplier(String userName, int passwrod, String phoneNumber, String email) {
		super(userName, passwrod, phoneNumber, email, 3);
	}
}
