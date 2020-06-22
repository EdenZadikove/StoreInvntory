package com.store.model;

public class Seller extends User {
	
	public Seller(String userName, int passwrod, String phoneNumber, String email) {
		super(userName, passwrod, phoneNumber, email, 2);
	}
}