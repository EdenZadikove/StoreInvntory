package controller;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import model.Model;
import model.Order;

public class Controller {
	private Model model_ = null;
	
	public Controller() {
		 model_ = new Model(); 
	}
	
	public boolean login(String email, int password) {
		LoginController loginController = new LoginController();
		loginController.login(email, password);
	}
	
	 /*
	public Map <Integer, Order> getOrders() {
		 Map <Integer, Order> orders = new  Hashtable <Integer, Order>();
		 orders = model_.getOrders();
		 return orders;
	}
	
	public int createOrder(String itemName, int quantity) {
		return model_.cretaeOrder(itemName, quantity);
	}
	
	public boolean deleteOrder(int orderId) {
		return model_.deleteOrder(orderId);
	}*/
}
