package model;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class UsersDB extends FileManager<Object>{
	
	private static String path_ = "..\\StoreInventory\\files\\users.txt";
	private static Map<String, User> users_ = new Hashtable<String, User>();
	private static UsersDB instance_ = null;
	
	@SuppressWarnings("unchecked")
	private UsersDB() throws IOException {
		super(path_);	
		users_ = (Map<String, User>) readFromFile(users_);
	}
	
	//Singletone
	public static  UsersDB getInstance() {
		if (instance_ == null) {
			try {
				instance_ = new UsersDB();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance_;
	}
	public static void resetInstance() {
		instance_ = null;
		users_ = null;
	}
	
	
	
	public Map<String, User> getUsers(){
		return users_;
	}
	
	public void setUsers( Map<String, User> users) throws IOException{
		writeToFile(users);
	}
}
