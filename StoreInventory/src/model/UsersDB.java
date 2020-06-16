package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class UsersDB extends FileManager<Object>{
	
	private static String path_ = "C:\\Users\\97250\\eclipse-workspace\\store-inventory-project---java\\StoreInventory\\files\\users.txt";
	private static Map<String, User> users_ = new Hashtable <String, User>();
	private static UsersDB instance = null;
	
	@SuppressWarnings("unchecked")
	private UsersDB() throws IOException {
		super(path_);	
		users_ = (Map<String, User>) readFromFile(users_);
	}
	
	//Singletone
	public static UsersDB getInstance() {
		if (instance == null) {
			try {
				instance = new UsersDB();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	
	
	
	public Map<String, User> getUsers(){
		return users_;
	}
	
	public void setUsers( Map<String, User> users) throws IOException{
		writeToFile(users);
	}
}
