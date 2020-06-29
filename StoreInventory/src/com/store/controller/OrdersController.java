package com.store.controller;

import java.io.IOException;
import java.util.ArrayList;
import com.store.model.Model;

public class OrdersController {
	
	private Model model_;
	
	public OrdersController() throws IOException {
		model_ = new Model();
	}
	
	public boolean isOrderExists(int orderId, String statusFilter,  String action) throws Exception {
		return model_.isOrderExists(orderId, statusFilter, action);
	}
	
	public int createOrder(String itemName, int quantity) throws IOException {
		return model_.cretaeOrder(itemName, quantity);
		
	}
	
	public boolean cancelOrder(int orderId) throws Exception {
		return model_.cancelOrder(orderId);
	}
	
	public boolean deleteOrder(int orderId) throws Exception {
		return model_.deleteOrder(orderId);
	}
	
	public String editOrder(int orderId, int quantity) {	
		//check if order ID is valid
		if(orderId < -1)
			throw new IllegalArgumentException("! Order Id must be more then 0");	
		if(quantity < 1 || quantity > 100)
			throw new IllegalArgumentException("! Quantity must be between 1 to 100");
	
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

	public String changeOrderStatus(int orderId, String action) throws IOException {
		return model_.changeOrderStatus(orderId, action);
	}
}
