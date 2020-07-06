package com.store.controller;

import java.util.ArrayList;
import com.store.model.services.OrdersService;

public class OrdersController {
	
	private OrdersService ordersService_;
	
	public OrdersController(){
		ordersService_ = new OrdersService();
	}
	
	public boolean isOrderExistsByFilter(int orderId, String statusFilter, String action) throws Exception {
		return ordersService_.isOrderExistsByFilter(orderId, statusFilter, action);
	}
	
	public int createOrder(String itemName, int quantity){	
		if(quantity < 1 || quantity > 100)
			throw new IllegalArgumentException("\n! Quantity must be between 1 to 100");
		return ordersService_.createOrder(itemName, quantity);
	}
	
	public boolean cancelOrder(int orderId, String CurrentStatus) throws Exception {
		if(CurrentStatus.equals("canceled") || CurrentStatus.equals("approved") || CurrentStatus.equals("denied"))
			throw new IllegalArgumentException("! Order id- " + orderId + " can not be canceled because its status is- '"+ CurrentStatus + "'.");
		if(!ordersService_.isOrderExists(orderId))
			throw new Exception("! Order id- " +  orderId + " does not exists.");
		return ordersService_.cancelOrder(orderId);
	}
	
	public boolean deleteOrder(int orderId) throws Exception {
		if(ordersService_.isOrderExists(orderId))
			return ordersService_.deleteOrder(orderId);
		throw new Exception("! Order id does not exists.");
		
	}
	
	public boolean editOrder(int orderId, int quantity) throws Exception{	
		if(!ordersService_.isOrderExists(orderId))
			throw new Exception("! Order id does not exists.");
		if(quantity < 1 || quantity > 100)
			throw new IllegalArgumentException("! Quantity must be between 1 to 100");
	
		return ordersService_.editOrder(orderId, quantity);
	}
	
	public void saveToFileOrders()  {
		ordersService_.saveToFileOrders();
	}
	
	public ArrayList<String> getOrders(String filterStatus) throws IllegalArgumentException{
		if(filterStatus == "" || filterStatus == null)
			throw new IllegalArgumentException("! Invalid filter. contact your administrator");
		return ordersService_.getOrders(filterStatus);
	}
	
	public int getOrdersSize() {
		return ordersService_.getOrdersSize();
	}
	
	public int itemsCounterByFilter(String filter) throws IllegalArgumentException {
		if(filter == "" || filter == null)
			throw new IllegalArgumentException("! Invalid filter. contact your administrator");
		return ordersService_.itemsCounterByFilter(filter);
	}

	public boolean changeOrderStatus(int orderId, String action){
		return ordersService_.changeOrderStatus(orderId, action);
	}
}
