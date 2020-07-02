package com.store.model.services;

import java.util.Map;

import com.store.model.UsersFactory;
import com.store.model.database.OrdersRepository;
import com.store.model.database.StoreRepository;
import com.store.model.database.UsersRepository;
import com.store.model.entities.Admin;
import com.store.model.entities.Seller;
import com.store.model.entities.Supplier;
import com.store.model.entities.User;

public class UserSessionService {
	private UsersRepository usersRepository_; 
	private UsersFactory usersFactory_;
	private Map<String, User> users_;
	private static User activeUser_;
	
	public UserSessionService() {
		usersRepository_ = UsersRepository.getInstance();
		users_ = usersRepository_.getUsers();
		usersFactory_ = new UsersFactory();
		initUsers();
	}
	
	public int signIn(String email, int password){
		validateUser(email, password);
		if(activeUser_ == null)
			return 0;
		createUser();
		return activeUser_.getUserType();
	}

	public void logout() {
		 UsersRepository.resetInstance();
		 StoreRepository.resetInstance();
		 OrdersRepository.resetInstance();
	}
	
	public int getUserType() {
		if(activeUser_ == null)
			return 0;
		return activeUser_.getUserType();
	}
	
	public String getUserName() {
		return activeUser_.getUserName();
	}
	
	public void saveToFileUsers() {
		usersRepository_.writeToFile(users_);
	}
	
	private void validateUser(String email, int password) {
		activeUser_ = usersRepository_.getUsers().get(email); 
		if(activeUser_ != null && activeUser_.getPassword() != password )
			activeUser_ = null;
	}
	
	private void createUser(){
		usersFactory_.getUser(activeUser_);
	}
	
	private void initUsers(){
		if(users_.size() == 0) {		
			User user1 = new Admin("Eden Zadikove", 123, "eden", 1, "Best-Store-Ever");
			User user2 = new Seller("Danny Bernshtein", 456, "danny", 2,  "Best-Store-Ever");
			User user3 = new Supplier("Sharom Mauda", 789, "sharon");

			users_.put(user1.getEmail(), user1);
			users_.put(user2.getEmail(), user2);
			users_.put(user3.getEmail(), user3);
			
			saveToFileUsers();
		}
	}
}
