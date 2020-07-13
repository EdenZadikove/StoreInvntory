package com.store.model.entities;

public class Seller extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8968345856982890890L;
	private int employeeNumber;
	private String storeName;
	
	public Seller(String userName, String passwrod, String email, int employeeNumber, String storeName) {
		super(userName, passwrod, email, 2);
		this.employeeNumber = employeeNumber;
		this.storeName = storeName;
	}

	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {
		return userName + "::" + password + "::" + email + "::" + userType + "::" + employeeNumber + "::" + storeName;
	}
}