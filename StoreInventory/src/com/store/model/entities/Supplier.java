package com.store.model.entities;

public class Supplier extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1861841800395194901L;

	public Supplier(String userName, String passwrod, String email) {
		super(userName, passwrod, email, 3);
	}

	@Override
	public String toString() {
		return userName + "::" + password + "::" + email + "::" + userType;
	}
}
