package com.store.model.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.store.model.UsersFactory;
import com.store.model.entities.Admin;
import com.store.model.entities.Seller;
import com.store.model.entities.Supplier;
import com.store.model.entities.User;
import com.store.model.repository.UsersRepository;

public class UsersService {
	private UsersRepository usersRepository_;
	private Map<String, User> users_;
	private UsersFactory usersFactory_;

	public UsersService(){
		usersRepository_ = UsersRepository.getInstance();
		users_ = usersRepository_.getUsers();
		usersFactory_ = new UsersFactory();
		initUsers();
	}
	
	public ArrayList<String> getAllUsers(){
		ArrayList<String> usersList = new ArrayList<String>();
		if(users_.size() != 0) { //NOT empty map
			for(Iterator<Map.Entry<String, User>> it = users_.entrySet().iterator(); it.hasNext(); ) {
			    Map.Entry<String, User> entry = it.next();
			    String row = "userName:" + entry.getValue().getUserName() + ";" +
			    		"email:" + entry.getValue().getEmail() + ";" +
			    		"userType:" + entry.getValue().getUserType() + ";";
			    usersList.add(row);
			}
		}
		return usersList;
	}
	
	public boolean createNewUser(String userName, String password, String email, int userType) {
		User user = null;
		
		if(isExistsUser(email))
			return false;
		
		switch(userType) {
		case 1:
			user = usersFactory_.createUser(userName, password, email, userType, availableEmployeeNumber(), "Best Store Ever");
			break;
		case 2:
			user = usersFactory_.createUser(userName, password, email, userType, availableEmployeeNumber(), "Best Store Ever");
			break;
		case 3:
			user = usersFactory_.createUser(userName, password, email, userType, 0, "");
			break;
		}
		users_.put(user.getEmail(), user);
		usersRepository_.setUsers_(users_);
		return true;
	}
	
	public boolean deleteUser(String email) {
		if(!isExistsUser(email))
			return false;
		users_.remove(email);
		usersRepository_.setUsers_(users_);
		return true;
	}
	
	public String getUserAsString(String email) {
		User user = users_.get(email);
		if(user == null)
			return null;
		switch(user.getUserType()) {
		case 1:
			return ((Admin)user).toString();
		case 2:
			return ((Seller)user).toString();
		case 3:
			return ((Supplier)user).toString();
		default:
			return null;
		}
	}
	
	public void saveToFileUsers(){
		usersRepository_.saveToFile();
	}
	
	private boolean isExistsUser(String email) {
		return users_.get(email.toLowerCase()) != null;
	}
	
	private int availableEmployeeNumber() {
		int maxKey = 2; //admin is 1
		//no users except of admin user
		if(users_.size() == 1)
			return maxKey;
		
		//Search for the max key
		int tempKey = 0;
		for (Map.Entry<String, User> currentEntry : users_.entrySet()) {
			if( currentEntry.getValue().getUserType() == 1)
				tempKey  = ((Admin)currentEntry.getValue()).getEmployeeNumber();
			else
				tempKey  = ((Seller)currentEntry.getValue()).getEmployeeNumber();
			
			if(tempKey > maxKey)
				 maxKey = tempKey;		 
		 }
		 return maxKey + 1;
	}
	
	private void initUsers(){
		if(users_.size() == 0) {		
			User users = new Admin("Eden_Zadikove", "123", "eden", 1, "Best-Store-Ever");
			users_.put(users.getEmail(), users);
			saveToFileUsers();
		}
	}
	

	
}
