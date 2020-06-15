package view;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import model.Admin;
import model.FileManager;
import model.Seller;
import model.Supplier;
import model.User;
import model.UsersDB;

public class TESTS_2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		UsersDB  usersDB_ = new UsersDB();
		
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
