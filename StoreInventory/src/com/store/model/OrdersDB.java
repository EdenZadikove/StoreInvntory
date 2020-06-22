package com.store.model;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class OrdersDB extends FileManager<Object> {

	private static String path_ = "..\\StoreInventory\\files\\orders.txt";
	private static Map<Integer, Order> orders_ = new Hashtable<Integer, Order>();
	private static OrdersDB instance_ = null;
	
	
	@SuppressWarnings("unchecked")
	private OrdersDB () throws IOException {
		super(path_);
		orders_ = (Map<Integer, Order>) readFromFile(orders_);
	}
	
	//Singletone
	public static  OrdersDB getInstance() {
		if (instance_ == null) {
			try {
				instance_ = new OrdersDB();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance_;
	}
	
	public static  void resetInstance() {
		instance_ = null;
		orders_ = null;
	}
	
	public Map<Integer, Order> getOrders(){
		return orders_;
		
	}
	
	public void setOrders(Map<Integer, Order> orders_) {
		OrdersDB.orders_ = orders_;
	}
	
	public void saveToFile() throws IOException {
		writeToFile(orders_);
	}
}

