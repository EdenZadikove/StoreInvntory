package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Supplier extends User {
	
	public Supplier(String userName, int passwrod, String phoneNumber, String email) {
		super(userName, passwrod, phoneNumber, email, 3);

	}

	public ArrayList<String> showOrdersTable() throws IOException {	
		ArrayList<String> ordersList = new ArrayList<String>();
		
		if(orders_.size() != 0) { //NOT empty map
		
			for(Iterator<Map.Entry<Integer, Order>> it = orders_.entrySet().iterator(); it.hasNext(); ) {
			    Map.Entry<Integer, Order> entry = it.next();
			    
			    System.out.println(entry.getValue().getOrderStatus());
			    if(entry.getValue().getOrderStatus().equals("pending")) {
			    	String row = "OrderId:" + entry.getValue().getOrderId() + ";" +
		    				     "ItemName:" + entry.getValue().getItemName() + ";" +
		    				     "Quantity:" + entry.getValue().getQuantity() + ";" +
		    				     "OrderStatus:" +  entry.getValue().getOrderStatus() + ";";
		    		ordersList.add(row);
			    }
			}
			
		}
		return ordersList;
	}
	
	public String changeOrderStatus(int orderId, String action) {
		
		Order order = isOrderExist(orderId);
		if  (order == null)
			return "Order does not exist!";
		String oldStatus = order.getOrderStatus();
		order.setOrderStatus(action);
		orders_.put(orderId, order); //update original map
		
		return "Order ID - " + orderId + " is successfully updated from " + oldStatus + " to " + order.getOrderStatus();
	}
	
	private Order isOrderExist(int orderId) {
		return orders_.get(orderId);
	}
	
	public void saveToFileOrders() throws IOException {
		ordersDB_.saveToFile();
	}
	

}
