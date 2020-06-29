package com.store.model.database;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.entities.Admin;
import com.store.model.entities.Seller;
import com.store.model.entities.Supplier;
import com.store.model.entities.User;

public class UsersDB extends FileManager<Object>{
	
	private static String path_ = "..\\StoreInventory\\files\\users.txt";
	private static Map<String, User> users_ = new Hashtable<String, User>();
	private static UsersDB instance_ = null;
	
	@SuppressWarnings("unchecked")
	private UsersDB() throws IOException {
		super(path_);	
		users_ = (Map<String, User>) readFromFile(users_);
		initUsers();
	}
	
	//Singletone
	public static UsersDB getInstance() {
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
	
	private void initUsers() throws IOException {
		if(users_.size() == 0) {		
			User user1 = new Admin("Eden Zadikove", 123, "050-2312093", "eden");
			User user2 = new Seller("Danny Bernshtein", 456, "050-2312093", "danny");
			User user3 = new Supplier("Sharom Mauda", 789, "050-2312093", "sharon");

			users_.put(user1.getEmail(), user1);
			users_.put(user2.getEmail(), user2);
			users_.put(user3.getEmail(), user3);
			
			writeToFile(users_);
		}
	}
}
