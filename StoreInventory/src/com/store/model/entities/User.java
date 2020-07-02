package com.store.model.entities;
import java.io.Serializable;


public abstract class User implements Serializable{
	
	protected String userName;
	protected int password;
	protected String email;
	protected int userType; //1 = admin, 2 = user, 3 = supplier 
	
	public User(String userName, int password, String email, int userType) {
		super();
		this.userName = userName;
		this.password = password;
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
	//public abstract String toString();

}
