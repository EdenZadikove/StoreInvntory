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
	private UserSessionService userSessionService_;
	private UsersFactory usersFactory_;

	public UsersService(){
		usersRepository_ = UsersRepository.getInstance();
		userSessionService_ = new UserSessionService();
		usersFactory_ = new UsersFactory();
	}
	
	public ArrayList<String> getAllUsers(){
		ArrayList<String> usersList = new ArrayList<String>();
		if(usersRepository_.getUsers().size() != 0) { //NOT empty map
			for(Iterator<Map.Entry<String, User>> it = usersRepository_.getUsers().entrySet().iterator(); it.hasNext(); ) {
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
		usersRepository_.getUsers().put(user.getEmail(), user);
		return true;
	}
	
	public boolean deleteUser(String email) throws Exception {
		//user can not delete his own user!
		String activeUserEmail = userSessionService_.getUserEmail();
		
		if(!isExistsUser(email))
			return false;
		
		if(activeUserEmail.equals(email.toLowerCase()))
				throw new Exception("! You can not delete your own user.");
		usersRepository_.getUsers().remove(email);
		return true;
	}
	
	public String getUserAsString(String email) {
		User user = usersRepository_.getUsers().get(email);
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
		return usersRepository_.getUsers().get(email.toLowerCase()) != null;
	}
	
	private int availableEmployeeNumber() {
		int maxKey = 2; //admin is 1
		//no users except of admin user
		if(usersRepository_.getUsers().size() == 1)
			return maxKey;
		
		//Search for the max key
		int tempKey = 0;
		for (Map.Entry<String, User> currentEntry : usersRepository_.getUsers().entrySet()) {
			if( currentEntry.getValue().getUserType() == 1)
				tempKey  = ((Admin)currentEntry.getValue()).getEmployeeNumber();
			else if( currentEntry.getValue().getUserType() == 2)
				tempKey  = ((Seller)currentEntry.getValue()).getEmployeeNumber();
			
			if(tempKey > maxKey)
				 maxKey = tempKey;		 
		 }
		 return maxKey + 1;
	}
}
