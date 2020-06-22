package com.store.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class OrdersService {
	
	private static OrdersDB ordersDB_;
	private Map<Integer, Order> orders_; 
	private StoreService storeService_;

	public OrdersService(){
		ordersDB_ = OrdersDB.getInstance();
		orders_ = ordersDB_.getOrders();
		storeService_ = new StoreService();
	}
	

	
	public int cretaeOrder(String itemName, int Quntity) throws IOException {
		int orderId = calculateOrderId();
		Order newOrder = new Order(orderId, itemName, Quntity);
		orders_.put(orderId, newOrder);
		ordersDB_.setOrders(orders_); //update orders map	
		return orderId;
	}

	private int calculateOrderId() throws IOException {
		int maxKey = 1;
		//empty orders table
		if(orders_.size() == 0)
			return maxKey;
		
		//Search for the max key
		for (Map.Entry<Integer, Order> currentEntry : orders_.entrySet()) {
			 int tempKey  = currentEntry.getKey();
			 if(tempKey > maxKey)
				 maxKey = tempKey;		 
		 }
		 return maxKey + 1;
	}
	
	public String cancelOrder(int orderId) {
		Order order = isOrderExist(orderId);
		if  (order == null)
			return "Order does not exist!";
		
		//check if order status is 'pending'
		if(order.getOrderStatus().equals("pending")) {
			order.setOrderStatus("canceled");
			orders_.put(orderId, order);
			return "Order id- " + orderId + " successfully canceled";
		}
		if(order.getOrderStatus().equals("canceled"))
			return "! Order id- " + orderId + " can not be canceled because its already done.";
		return "! Order id- " + orderId + " can not be canceled because its already " + order.getOrderStatus() + " by the supplier.";
	}
	
	public String deleteOrder(int orderId) {
		Order order = isOrderExist(orderId);
		if  (order == null)
			return "Order does not exist!";
		
		//check if order status is 'canceled, approved or denied'
		if(order.getOrderStatus().equals("canceled") || order.getOrderStatus().equals("approved") || order.getOrderStatus().equals("denied")) {
			orders_.remove(orderId);
			return "Order id- " + orderId + " successfully deleted";
		}
		return "Order id- " + orderId + " can not be deleted because its waiting for supplier's response.\nIf"
					+ " you want to delete this order - 1. Cancel this order\n"
					+ "                                   2. Delete this order.";	
	}
	
	public String editOrder(int orderId, int quantity) {
		Order order = isOrderExist(orderId);
		if  (order == null)
			return "Order does not exist!";
		if(order.getOrderStatus().equals("canceled") || order.getOrderStatus().equals("approved") || order.getOrderStatus().equals("denied"))
			return "Order id- " + orderId + " can not be edited because its already " + order.getOrderStatus() + " by the supplier.";
		if(order.getQuantity() == quantity) {
			return "Order quantity is already set to - " + order.getQuantity();
		}
		order.setQuantity(quantity);
		return "Order quantity successfully updated to- " + order.getQuantity();	
	}
		
	public int getOrdersSize() {
		return orders_.size();
	}
	
	public int itemsCounterByFilter(String filter) {
		int itemsCounter = 0;
		if(filter.equals("all"))
			return orders_.size();
		for (Map.Entry<Integer, Order> entry : orders_.entrySet()) 
			if(entry.getValue().getOrderStatus().equals(filter)) itemsCounter++;
		return itemsCounter;
	}
	
	public String changeOrderStatus(int orderId, String action) {
		
		Order order = isOrderExist(orderId);
		if  (order == null || !order.getOrderStatus().equals("pending"))
			return "Order does not exist!";
		String oldStatus = order.getOrderStatus();
		order.setOrderStatus(action);
		orders_.put(orderId, order); //update original map
		
		if(action == "approved") {//update store inventory
			storeService_.updateStoreInventory(order.getItemName(), order.getQuantity());
		}
		return "Order ID - " + orderId + " is successfully updated from " + oldStatus + " to " + order.getOrderStatus();
	}
	

	//convert from map to array list of strings
	public ArrayList<String> getOrders(String filterStatus) {	
		ArrayList<String> ordersList = new ArrayList<String>();
		if(orders_.size() != 0) { //NOT empty map
			for(Iterator<Map.Entry<Integer, Order>> it = orders_.entrySet().iterator(); it.hasNext(); ) {
			    Map.Entry<Integer, Order> entry = it.next();
			    if(entry.getValue().getOrderStatus().equals(filterStatus)) {
			    	String row = "OrderId:" + entry.getValue().getOrderId() + ";" +
		    				     "ItemName:" + entry.getValue().getItemName() + ";" +
		    				     "Quantity:" + entry.getValue().getQuantity() + ";" +
		    				     "OrderStatus:" +  entry.getValue().getOrderStatus() + ";" +
		    				     "OrderDate:" + entry.getValue().getOrderDate() + ";";
		    		ordersList.add(row);
			    }
			    else  if(filterStatus.equals("all")) {
			    	String row = "OrderId:" + entry.getValue().getOrderId() + ";" +
	    				     "ItemName:" + entry.getValue().getItemName() + ";" +
	    				     "Quantity:" + entry.getValue().getQuantity() + ";" +
	    				     "OrderStatus:" +  entry.getValue().getOrderStatus() + ";" +
	    				     "OrderDate:" + entry.getValue().getOrderDate() + ";";
	    		ordersList.add(row);
			    }
			}
		}
		return ordersList;
	}
	
	public boolean isOrderExistBoolean(int orderId) {
		return !(orders_.get(orderId) == null);
	}
	
	public void saveToFileOrders() throws IOException {
		ordersDB_.writeToFile(orders_);
	}
	
	private Order isOrderExist(int orderId) {
		return orders_.get(orderId);
	}
}
