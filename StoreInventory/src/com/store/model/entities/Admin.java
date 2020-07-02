package com.store.model.entities;

public class Admin extends User {
	private int employeeNumber;
	private String storeName;
	
	public Admin(String userName, int passwrod, String email, int employeeNumber, String storeName){
		super(userName, passwrod, email, 1);
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
