package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Supplier extends User {
	
	private static OrdersDB ordersDB_ = null;
	private Map<Integer, Order> orders_  = null; 
	
	public Supplier(String userName, int passwrod, String phoneNumber, String email) {
		super(userName, passwrod, phoneNumber, email, 3);
		ordersDB_ = OrdersDB.getInstance();
		orders_ = ordersDB_.getOrders();
		filterOrders();
	}

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
	
	
	private void filterOrders() {
		//remove from map all orders with status "approved" and "denied"
		
		if(orders_.size() != 0) { //NOT empty map
			for(Iterator<Map.Entry<Integer, Order>> it = orders_.entrySet().iterator(); it.hasNext(); ) {
			    Map.Entry<Integer, Order> entry = it.next();
			    if(entry.getValue().getOrderStatus().equals("denied") || 
			       entry.getValue().getOrderStatus().equals("approved") || 
			       entry.getValue().getOrderStatus().equals("canceled")) {
			    	it.remove();
			    }
			}
		}
	}

	@Override
	public void logOut() {
		// TODO Auto-generated method stub

	}
}
