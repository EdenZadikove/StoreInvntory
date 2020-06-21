package model;

import java.util.ArrayList;

public class Seller extends User {
	
	public Seller(String userName, int passwrod, String phoneNumber, String email) {
		super(userName, passwrod, phoneNumber, email, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> getOrders() {
		// TODO Auto-generated method stub
		return null;
	}
}