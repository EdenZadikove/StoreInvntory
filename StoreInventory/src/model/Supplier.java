package model;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Supplier extends User {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L; //?
	//private OrdersDB ordersDB_ = new OrdersDB();
	//private Map <Integer, Order> myOrders_ = new Hashtable <Integer, Order>();
	
		public Supplier(String userName, int passwrod, String phoneNumber, String email) {
			super(userName, passwrod, phoneNumber, email, 3);

		}
	
	//public Supplier(String userName, int passwrod, String phoneNumber, String email) throws IOException {
		//super(userName, passwrod, phoneNumber, email, 3);
		//this.myOrders_ = (Map <Integer, Order>) ordersDB_.readFromFile(myOrders_);
		//filterOrders();
	//}
	

	
	private void filterOrders() {
		//remove from map all orders with status "approved" and "denied"
		
		if(myOrders_.size() != 0) { //NOT empty map
			for(Iterator<Map.Entry<Integer, Order>> it = myOrders_.entrySet().iterator(); it.hasNext(); ) {
			    Map.Entry<Integer, Order> entry = it.next();
			    if(entry.getValue().getOrderStatus().equals("denied") || entry.getValue().getOrderStatus().equals("approve")) {
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
