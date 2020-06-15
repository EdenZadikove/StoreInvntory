package model;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class LoginRepository {
	
	private UsersDB users_;

	public LoginRepository() {
		try {
			users_ = new UsersDB();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		 
	}
	
	public User validateData(String email, int password) {
		User user = users_.getUsers().get(email); 
		if(user == null || user.getPassword() != password )
			return null;
		return user;
	}
}
