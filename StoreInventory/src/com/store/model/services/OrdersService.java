package com.store.model.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.store.model.entities.Order;
import com.store.model.repository.OrdersRepository;

public class OrdersService {
	
	private OrdersRepository ordersRepository_;
	private StoreService storeService_;

	public OrdersService(){
		ordersRepository_ = OrdersRepository.getInstance();
		storeService_ = new StoreService();
	}
		
	public int createOrder(String itemName, int Quntity){
		int orderId = calculateOrderId(); 
		ordersRepository_.getOrders().put(orderId, new Order(orderId, itemName, Quntity));
		return orderId;
	}
	
	public boolean cancelOrder(int orderId) throws Exception {
		Order order = ordersRepository_.getOrders().get(orderId);
		if(!isOrderExists(orderId))
			return false;
		if(!order.getOrderStatus().equals("pending"))
			throw new Exception("! Order id- " + orderId + " can not be canceled bacause it's already done.");
		
		order.setOrderStatus("canceled");
		ordersRepository_.getOrders().put(orderId, order);
		return true;
	}
	
	public boolean deleteOrder(int orderId) throws Exception{
		Order order = ordersRepository_.getOrders().get(orderId);
		if(!isOrderExists(orderId))
			return false;
		
		if(order.getOrderStatus().equals("pending"))
			throw new Exception("! Order id- " + orderId + " can not be deleted because its waiting for supplier's response.\nIf"
					+ " you want to delete this order - 1. Cancel this order\n"
					+ "                                   2. Delete this order.");
		
		ordersRepository_.getOrders().remove(orderId);
		return true;
	}
	
	public boolean editOrder(int orderId, int quantity) throws Exception {
		Order order = ordersRepository_.getOrders().get(orderId);
		if(!isOrderExists(orderId))
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
		return ordersRepository_.getOrders().size();
	}
	
	public int itemsCounterByFilter(String filter) {
		int itemsCounter = 0;
		if(filter.equals("all"))
			return ordersRepository_.getOrders().size();
		for (Map.Entry<Integer, Order> entry : ordersRepository_.getOrders().entrySet()) 
			if(entry.getValue().getOrderStatus().equals(filter)) itemsCounter++;
		return itemsCounter;
	}
	
	public boolean changeOrderStatus(int orderId, String action){
		
		Order order = ordersRepository_.getOrders().get(orderId);
		if  (order == null || !order.getOrderStatus().equals("pending"))
			return false;
		order.setOrderStatus(action);
		ordersRepository_.getOrders().put(orderId, order);
		if(action == "approved") {//update store inventory
			storeService_.addProductToStoreInventory(order.getItemName(), order.getQuantity());
		}
		return true;
	}
	
	public ArrayList<String> getOrders(String filterStatus) {	
		//convert from map to array list of strings
		ArrayList<String> ordersList = new ArrayList<String>();
		if(ordersRepository_.getOrders().size() != 0) { //NOT empty map
			for(Iterator<Map.Entry<Integer, Order>> it = ordersRepository_.getOrders().entrySet().iterator(); it.hasNext(); ) {
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
		Order order = ordersRepository_.getOrders().get(orderId);
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
	
	public boolean isOrderExists(int orderId) {
		return (ordersRepository_.getOrders().get(orderId) != null);
	}
	
	public void saveToFileOrders() {
		ordersRepository_.saveToFile();
		storeService_.saveToFileStore();
	}
	
	private int calculateOrderId(){
		int maxKey = 1;
		//empty orders table
		if(ordersRepository_.getOrders().size() == 0)
			return maxKey;
		
		//Search for the max key
		for (Map.Entry<Integer, Order> currentEntry : ordersRepository_.getOrders().entrySet()) {
			 int tempKey  = currentEntry.getKey();
			 if(tempKey > maxKey)
				 maxKey = tempKey;		 
		 }
		 return maxKey + 1;
	}
}