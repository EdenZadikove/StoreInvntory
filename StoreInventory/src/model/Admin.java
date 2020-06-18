package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Admin extends User {

	public Admin(String userName, int passwrod, String phoneNumber, String email) throws IOException {
		super(userName, passwrod, phoneNumber, email, 1);
		
	}

	/* ORDERS functions */
	
	public ArrayList<String> showOrdersTable() throws IOException {
		
		ArrayList<String> ordersList = new ArrayList<String>();
		
		for (Map.Entry<Integer, Order> entry : orders_.entrySet()) {
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
		
		int orderId = calculateOrderId();
		Order newOrder = new Order(orderId, itemName, Quntity);
		orders_.put(orderId, newOrder);
		ordersDB_.setOrders(orders_);
		
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
			return "Order id- " + orderId + " can not be canceled because its already done.";
		return "Order id- " + orderId + " can not be canceled because its already " + order.getOrderStatus() + " by the supplier.";
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
	
	private Order isOrderExist(int orderId) {
		return orders_.get(orderId);
	}
	
	public int getOrdersSize() {
		return orders_.size();
	}

	//STORE Functions
	
	public String editPrice(String itemName, double price) {
		Product product  = products_.get(itemName);
		double oldPrice = product.getPrice();
		products_.get(itemName).setPrice(price);
		return (itemName + " price successfully update from: " + oldPrice + " to " + product.getPrice());
	}
}
