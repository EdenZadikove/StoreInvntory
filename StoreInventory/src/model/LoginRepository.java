package model;

public class LoginRepository {
	
	private UsersDB users_ = null;

	public LoginRepository() {
		users_ = UsersDB.getInstance();
	}
	
	public User validateData(String email, int password) {
		User user = users_.getUsers().get(email); 
		if(user == null || user.getPassword() != password )
			return null;
		return user;
	}
}
