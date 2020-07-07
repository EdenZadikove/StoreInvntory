package com.store.controller;

import java.util.ArrayList;
import com.store.model.services.OrdersService;

public class OrdersController {
	
	private OrdersService ordersService_;
	
	public OrdersController(){
		ordersService_ = new OrdersService();
	}
	
	public boolean isOrderExistsByFilter(int orderId, String statusFilter, String action) throws Exception {
		if(orderId < 0)
			throw new IllegalArgumentException("! Order Id must be more then 0. Please try again.");
		return ordersService_.isOrderExistsByFilter(orderId, statusFilter, action);
	}
	
	public int createOrder(String itemName, int quantity){	
		if(quantity < 1 || quantity > 100)
			throw new IllegalArgumentException("! Quantity must be between 1 to 100");
		return ordersService_.createOrder(itemName, quantity);
	}
	
	public boolean cancelOrder(int orderId) throws Exception {
		if(orderId < 0)
			throw new IllegalArgumentException("! Order Id must be more then 0. Please try again.");
		return ordersService_.cancelOrder(orderId);
	}
	
	public boolean deleteOrder(int orderId) throws Exception {
		if(orderId < 0)
			throw new IllegalArgumentException("! Order Id must be more then 0. Please try again.");
		return ordersService_.deleteOrder(orderId);
		
	}
	
	public boolean editOrder(int orderId, int quantity) throws Exception{	
		if(orderId < 0)
			throw new IllegalArgumentException("! Order Id must be more then 0. Please try again.");
		if(quantity < 1 || quantity > 100)
			throw new IllegalArgumentException("! Quantity must be between 1 to 100 units.");
		return ordersService_.editOrder(orderId, quantity);	
	}
	
	public void saveToFileOrders()  {
		ordersService_.saveToFileOrders();
	}
	
	public ArrayList<String> getOrders(String filterStatus) throws IllegalArgumentException{
		if(filterStatus == "" || filterStatus == null)
			throw new IllegalArgumentException("! Invalid filter. Please contact your administrator");
		return ordersService_.getOrders(filterStatus);
	}
	
	public int getOrdersSize() {
		return ordersService_.getOrdersSize();
	}
	
	public int itemsCounterByFilter(String filter) throws IllegalArgumentException {
		if(filter == "" || filter == null)
			throw new IllegalArgumentException("! Invalid filter. Please contact your administrator");
		return ordersService_.itemsCounterByFilter(filter);
	}

	public boolean changeOrderStatus(int orderId, String action){
		if(orderId < 0)
			throw new IllegalArgumentException("! Order Id must be more then 0. Please try again.");
		return ordersService_.changeOrderStatus(orderId, action);
	}
}
