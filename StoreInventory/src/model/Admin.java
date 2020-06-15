package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Admin extends User {
	
	private static OrdersDB ordersDB_ = null;
	//private UsersDB usersDB_ = new UsersDB();
	
	public Admin(String userName, int passwrod, String phoneNumber, String email) throws IOException {
		super(userName, passwrod, phoneNumber, email, 1);
		ordersDB_ = OrdersDB.getInstance();
	}

	@Override
	public void logOut() {
		// TODO Auto-generated method stub
	}
	
	
	/* ORDERS functions */
	
	public ArrayList<String> showOrdersTable() throws IOException {
		
		ArrayList<String> ordersList = new ArrayList<String>();
		
		for (Map.Entry<Integer, Order> entry : ordersDB_.getOrders().entrySet()) {
			String row = "OrderId:" + entry.getValue().getOrderId() + ";" +
						 "ItemName:" + entry.getValue().getItemName() + ";" +
						 "Quantity:" + entry.getValue().getQuantity() + ";" +
						 "OrderStatus:" +  entry.getValue().getOrderStatus() + ";";
			ordersList.add(row);
		}
		return ordersList;
	}
	
	@SuppressWarnings("unchecked")
	public int cretaeOrder(String itemName, int Quntity) throws IOException {
		
		Map<Integer, Order> orders_ = new Hashtable <Integer, Order>();
		
		int orderId = calculateOrderId();
		Order newOrder = new Order(orderId, itemName, Quntity);
		orders_ = ordersDB_.getOrders();
		orders_.put(orderId, newOrder);
		ordersDB_.setOrders(orders_);
		
		return orderId;
	}

	private int calculateOrderId() throws IOException {
		
		Map <Integer, Order> mapOrders = ordersDB_.getOrders();
		int maxKey = 1;
		
		//empty orders table
		if(mapOrders.size() == 0)
			return maxKey;
		
		//Search for the max key
		for (Map.Entry<Integer, Order> currentEntry : mapOrders.entrySet()) {
			 int tempKey  = currentEntry.getKey();
			 if(tempKey > maxKey)
				 maxKey = tempKey;		 
		 }
		 return maxKey + 1;
	}
	
	public String cancelOrder(int orderId) {
		Map <Integer, Order> mapOrders = ordersDB_.getOrders();
		Order order = mapOrders.get(orderId);
		if  (order == null)
			return "Order does not exist!";
		
		//check if order status is 'pending'
		if(order.getOrderStatus().equals("pending")) {
			order.setOrderStatus("canceled");
			mapOrders.put(orderId, order);
			return "Order id- " + orderId + " successfully canceled";
		}
		if(order.getOrderStatus().equals("canceled"))
			return "Order id- " + orderId + " can not be canceled because its already done.";
		return "Order id- " + orderId + " can not be canceled because its already " + order.getOrderStatus() + " by the supplier.";
	}
	
	public Order isOrderExist(Map <Integer, Order> mapOrders,  int orderId) {
		
		Order order = mapOrders.get(orderId);
		return order;
		
	}
	
	
	public void saveToFile() throws IOException {
		ordersDB_.saveToFile();
	}
}











