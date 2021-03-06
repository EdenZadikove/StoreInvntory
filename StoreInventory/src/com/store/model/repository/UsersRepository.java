package com.store.model.repository;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.entities.Admin;
import com.store.model.entities.User;

public class UsersRepository{
	
	private static String path_ = "..\\StoreInventory\\files\\users.txt";
	private  Map<String, User> users_ = new Hashtable<String, User>();
	private static UsersRepository instance_ = null;
	private FileManager<Map<String, User>> fileManager; 
	
	private UsersRepository() throws IOException {
		this.fileManager = new FileManager<>(path_);
		users_ = fileManager.readFromFile(users_);
		initUsers();
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
	
	public void resetInstance() {
		instance_ = null;
		users_ = null;
	}
	
	public Map<String, User> getUsers(){
		return users_;
	}
	
	public void setUsers_(Map<String, User> users_) {
		this.users_ = users_;
	}

	public static void setPath_(String newPath) {
		path_ = newPath;
	}

	public void saveToFile() {
		this.fileManager.writeToFile(users_);
	}
	
	private void initUsers(){
		if(users_.size() == 0) {		
			User users = new Admin("Eden_Zadikove", "123", "eden@gmail.com", 1, "Best-Store-Ever");
			users_.put(users.getEmail(), users);
			saveToFile();
		}
	}
}
