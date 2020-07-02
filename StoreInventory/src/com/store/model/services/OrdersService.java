package com.store.model.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import com.store.model.database.OrdersRepository;
import com.store.model.entities.Order;

public class OrdersService {
	
	private static OrdersRepository ordersRepository_;
	private Map<Integer, Order> orders_; 
	private StoreService storeService_;

	public OrdersService(){
		ordersRepository_ = OrdersRepository.getInstance();
		orders_ = ordersRepository_.getOrders();
		storeService_ = new StoreService();
	}
		
	public int createOrder(String itemName, int Quntity){
		int orderId = calculateOrderId(); 
		Order newOrder = new Order(orderId, itemName, Quntity);
		orders_.put(orderId, newOrder);
		return orderId;
	}

	private int calculateOrderId(){
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
	
	public boolean cancelOrder(int orderId) throws Exception {
		Order order = isOrderExists(orderId);
		if  (order == null)
			return false;
		//check if order status is 'pending'
		if(order.getOrderStatus().equals("pending")) {
			order.setOrderStatus("canceled");
			orders_.put(orderId, order);
			return true;
		}
		if(order.getOrderStatus().equals("canceled"))
			throw new Exception("! Order id- " + orderId + " can not be canceled because its already done.");
		throw new Exception("! Order id- " + orderId + " can not be canceled because its already " + order.getOrderStatus() + " by the supplier.");
	}
	
	public boolean deleteOrder(int orderId) throws Exception {
		Order order = isOrderExists(orderId);
		if  (order == null)
			return false;
		//check if order status is 'canceled, approved or denied'
		if(order.getOrderStatus().equals("canceled") || order.getOrderStatus().equals("approved") || order.getOrderStatus().equals("denied")) {
			orders_.remove(orderId);
			return true;
		}
		throw new Exception("Order id- " + orderId + " can not be deleted because its waiting for supplier's response.\nIf"
					+ " you want to delete this order - 1. Cancel this order\n"
					+ "                                   2. Delete this order.");	
	}
	
	public boolean editOrder(int orderId, int quantity) throws Exception {
		Order order = isOrderExists(orderId);
		if  (order == null)
			return false;
		if(order.getOrderStatus().equals("canceled") || order.getOrderStatus().equals("approved") || order.getOrderStatus().equals("denied"))
			throw new Exception("Order id- " + orderId + " can not be edited because its already " + order.getOrderStatus() + " by the supplier.");
		if(order.getQuantity() == quantity) {
			throw new Exception("Order quantity is already set to - " + order.getQuantity());
		}
		order.setQuantity(quantity);
		return true;
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
	
	public boolean changeOrderStatus(int orderId, String action){
		
		Order order = isOrderExists(orderId);
		if  (order == null || !order.getOrderStatus().equals("pending"))
			return false;
		order.setOrderStatus(action);
		orders_.put(orderId, order); //update original map
		
		if(action == "approved") {//update store inventory
			storeService_.addProductToStoreInventory(order.getItemName(), order.getQuantity());
		}
		return true;
	}
	
	public ArrayList<String> getOrders(String filterStatus) {	
		//convert from map to array list of strings
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
	
	public boolean isOrderExistsByFilter(int orderId, String statusFilter, String action) throws Exception {
		Order order = orders_.get(orderId);
		if(order == null) return false;
		String orderStatus = order.getOrderStatus();
		
		switch(action) {
		case "approved":
			return  orderStatus.equals("pending");
		case "denied":
			return  orderStatus.equals("pending");
		case "cancel":
			if(!orderStatus.equals("pending")) {
				throw new Exception("! Order id- " + orderId + 
						" can not be canceled because it's status is: '" +
						orderStatus + "'.");
			}
			break;
		case "delete":
			if(orderStatus.equals("pending")) {
				throw new Exception("! Order id- " + orderId + 
						" can not be deleted because it's status is: '" +
						orderStatus + "'.");
			}
			break;
		case "edit":
			if(!orderStatus.equals("pending")) {
				throw new Exception("! Order id- " + orderId + 
						" can not be edited because it's status is: '" +
						orderStatus + "'.");
			}
			break;
		}
		return true;
	}
	
	public void saveToFileOrders() {
		ordersRepository_.writeToFile(orders_);
	}
	
	private Order isOrderExists(int orderId) {
		return orders_.get(orderId);
	}
}
