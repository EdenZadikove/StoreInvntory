package controller;

import java.io.IOException;
import model.User;
import model.Admin;
import model.Seller;
import model.Supplier;

	public class UsersDEMO {

		public static void main(String[] args) throws Exception {
			// TODO Auto-generated method stub
			User user1 = new Admin("Eden Zadikove", 123, "050-2312093", "eden");
			User user2 = new Seller("Danny Bernshtein", 456, "050-2312093", "danny");
			User user3 = new Supplier("Sharom Mauda", 789, "050-2312093", "sharon");
			
			try {
				UsersManager a = null;
				a = a.getInstance();
				System.out.println(a.getSetSize());
				
				a.createUser(user1);
				a.createUser(user2);
				a.createUser(user3);
				System.out.println(user1.getUserName() + "- isExist result: " +a.isUserExists(user1));
				System.out.println(user2.getUserName() + " isExist result: " + a.isUserExists(user2)); 
				System.out.println(user3.getUserName() + " isExist result: " + a.isUserExists(user3)); 
				
				System.out.println(a.getSetSize());
				a.saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

