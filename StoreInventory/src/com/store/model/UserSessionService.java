package com.store.model;

import java.io.IOException;

public class UserSessionService {
	private UsersDB users_; 
	private UsersFactory usersFactory_;
	
	public UserSessionService() {
		users_ = UsersDB.getInstance();
		usersFactory_ = new UsersFactory();
	}
	
	public User signIn(String email, int password) throws IOException {
		User user  = validateUser(email, password);
		if(user == null)
			return null;
		createUser(user);
		return user;
	}

	public void logOut() {
		 UsersDB.resetInstance();
		 StoreDB.resetInstance();
		 OrdersDB.resetInstance();
	}
	
	public int getUserType(User user) {
		if(user == null)
			return 0;
		return user.getUserType();
	}
	
	public String getUserName(User user) {
		return user.getUserName();
	}
	
	private User validateUser(String email, int password) {
		User user = users_.getUsers().get(email); 
		if(user == null || user.getPassword() != password )
			return null;
		return user;
	}
	
	private void createUser(User user) throws IOException {
		usersFactory_.getUser(user);
	}
	

}
