package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import model.Model;
import model.OrdersDB;

public class OrdersController {
	
	private Model model_;
	
	
	public OrdersController() throws IOException {
		model_ = new Model();
	}
	
	public int createOrder(String itemName, int quantity) throws IOException {
		return model_.cretaeOrder(itemName, quantity);
		
	}
	
	public String cancelOrder(int orderId) {
		return model_.cancelOrder(orderId);
	}
	
	public void saveToFile() throws IOException {
		model_.saveToFile_orders();
	}
	
	public ArrayList<String> showOrdersTable() throws IOException{
		return model_.showOrdersTable();
	}
}