package com.store.model.database;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.entities.User;

public class UsersRepository extends FileManager<Object>{
	
	private static String path_ = "..\\StoreInventory\\files\\users.txt";
	private static Map<String, User> users_ = new Hashtable<String, User>();
	private static UsersRepository instance_ = null;

	@SuppressWarnings("unchecked")
	private UsersRepository() throws IOException {
		super(path_);	
		users_ = (Map<String, User>) readFromFile(users_);
	}
	
	//Singleton
	public static UsersRepository getInstance() {
		if (instance_ == null) {
			try {
				instance_ = new UsersRepository();
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
}
