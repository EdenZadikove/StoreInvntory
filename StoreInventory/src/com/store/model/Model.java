package com.store.model;
import java.util.ArrayList;
import java.util.Map;

import com.store.model.entities.User;
import com.store.model.services.OrdersService;
import com.store.model.services.StoreService;
import com.store.model.services.UserSessionService;

import java.io.IOException;

public class Model    {
	
	private static User user_;  
	private OrdersService ordersService_;
	private StoreService storeService_;
	private UserSessionService userSessionService_; 
	
	public Model() {
		ordersService_ = new OrdersService();
		storeService_ = new StoreService();
		userSessionService_ = new UserSessionService();
	}
	
	//User Functions
		
	public int signIn(String email, int password) throws IOException  {
		user_ = userSessionService_.signIn(email, password);
		return userSessionService_.getUserType(user_);
	}
	
	public String getUserName() {
		return userSessionService_.getUserName(user_);
	}
	
	public void logout() {
		userSessionService_.logOut();
	}
	
	/*STORE Functions*/
	
	public ArrayList<String> getProductDetails(String product) {
		 return storeService_.getProductDetails(product);
	}
	
	public Map<String, String> getProducts() throws IOException{
		return storeService_.getProducts(user_.getUserType());
	}
	
	public String editPrice(String itemName, double price) {
		return storeService_.editPrice(itemName, price);
	}
	
	public String removeProduct(String itemName) {
		return storeService_.removeProduct(itemName);
	}
	
	public void saveToFileStore() throws IOException {
		storeService_.saveToFile();
	}
	
	/*ORDERS Functions*/
	
	public boolean isOrderExists(int orderId, String statusFilter,  String action) throws Exception {
		return ordersService_.isOrderExistBoolean(orderId, statusFilter, action);
	}
	
	public int cretaeOrder(String itemName, int quantity) throws IOException {//admin
		return ordersService_.cretaeOrder(itemName, quantity);
	}
	
	public boolean cancelOrder(int orderId) throws Exception { //admin
		return ordersService_.cancelOrder(orderId);
	}
	
	public boolean deleteOrder(int orderId) throws Exception { //admin
		return ordersService_.deleteOrder(orderId);
	}

	public String editOrder(int orderId, int quantity) { //admin
		return ordersService_.editOrder(orderId, quantity);
	}
	
	public int getOrdersSize() { //admin
		return ordersService_.getOrdersSize();
	}

	public void saveToFileOrders() throws IOException {
		ordersService_.saveToFileOrders();
	}
	
	public int itemsCounterByFilter(String filter) {
		return ordersService_.itemsCounterByFilter(filter);
	}
	
	public ArrayList<String> getOrders(String filterStatus) throws IOException{ //Supplier and Admin
		return ordersService_.getOrders(filterStatus);
	}
	
	public String changeOrderStatus(int orderId, String action) throws IOException { //supplier
		return ordersService_.changeOrderStatus(orderId, action);
	}
}