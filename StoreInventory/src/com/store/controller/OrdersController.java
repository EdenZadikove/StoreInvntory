package com.store.controller;

import java.io.IOException;
import java.util.ArrayList;
import com.store.model.Model;

public class OrdersController {
	
	private Model model_;
	
	public OrdersController() throws IOException {
		model_ = new Model();
	}
	
	public boolean isOrderExists(int orderId) {
		return model_.isOrderExists(orderId);
	}
	
	public int createOrder(String itemName, int quantity) throws IOException {
		return model_.cretaeOrder(itemName, quantity);
		
	}
	
	public String cancelOrder(int orderId) {
		return model_.cancelOrder(orderId);
	}
	
	public String deleteOrder(int orderId) {
		return model_.deleteOrder(orderId);
	}
	
	public String editOrder(int orderId, int quantity) {
		return model_.editOrder(orderId, quantity);
	}
	
	public void saveToFile() throws IOException {
		model_.saveToFileOrders();
	}
	
	public ArrayList<String> getOrders(String filterStatus) throws IOException{
		return model_.getOrders(filterStatus);
	}
	
	public int getOrdersSize() {
		return model_.getOrdersSize();
	}
	
	public int itemsCounterByFilter(String filter) {
		return model_.itemsCounterByFilter(filter);
	}

	public String changeOrderStatus(int orderId, String action) {
		return model_.changeOrderStatus(orderId, action);
	}
}