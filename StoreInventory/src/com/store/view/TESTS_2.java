package com.store.view;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.Admin;
import com.store.model.FileManager;
import com.store.model.Seller;
import com.store.model.Supplier;
import com.store.model.User;
import com.store.model.UsersDB;

public class TESTS_2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		UsersDB  usersDB_ = UsersDB.getInstance();
		
		Map<String, User> users_ = new Hashtable <String, User>();
		User user1 = new Admin("Eden Zadikove", 123, "050-2312093", "eden");
		User user2 = new Seller("Danny Bernshtein", 456, "050-2312093", "danny");
		User user3 = new Supplier("Sharom Mauda", 789, "050-2312093", "sharon");
		users_ = usersDB_.getUsers();
		users_.put(user1.getEmail(), user1);
		users_.put(user2.getEmail(), user2);
		users_.put(user3.getEmail(), user3);
		usersDB_.writeToFile(users_);
	}
}
