package com.store.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;

public abstract class User implements Serializable{
	
	private String userName;
	private int password;
	private String phoneNumber;
	private String email;
	private int userType; //1 = admin, 2 = user, 3 = supplier 
	
	public User(String userName, int password, String phoneNumber, String email, int userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.userType = userType;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getUserType()
	{
		return userType;	
	}
	
	public void setUserType(int userType)
	{
		this.userType = userType;
	}
	
	public String toString()
	{
		return userName + "::" + password + "::" +
				phoneNumber + "::" + email + "::" + userType;
	}
}
