package com.store.model.services;

import com.store.model.entities.User;
import com.store.model.repository.UsersRepository;

public class UserSessionService {
	private UsersRepository usersRepository_; 
	private static User activeUser_;
	
	public UserSessionService() {
		usersRepository_ = UsersRepository.getInstance();
	}
	
	public int signIn(String email, String password){
		validateUser(email, password);
		if(activeUser_ == null)
			return 0;
		return activeUser_.getUserType();
	}

	public void logout() {
		usersRepository_.resetInstance();
		usersRepository_.resetInstance();
		usersRepository_.resetInstance();
	}
	
	public int getUserType() {
		if(activeUser_ == null)
			return 0;
		return activeUser_.getUserType();
	}
	
	public String getUserName() {
		return activeUser_.getUserName();
	}
	
	private void validateUser(String email, String password) {
		activeUser_ = usersRepository_.getUsers().get(email); 
		if(activeUser_ != null && !activeUser_.getPassword().equals(password))
			activeUser_ = null;
	}
}
