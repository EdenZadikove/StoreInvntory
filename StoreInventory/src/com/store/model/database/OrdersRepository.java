package com.store.model.database;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.entities.Order;


public class OrdersRepository extends FileManager<Object> {

	private static String path_ = "..\\StoreInventory\\files\\orders.txt";
	private static Map<Integer, Order> orders_ = new Hashtable<Integer, Order>();
	private static OrdersRepository instance_ = null;
	
	@SuppressWarnings("unchecked")
	private OrdersRepository () throws IOException {
		super(path_);
		orders_ = (Map<Integer, Order>) readFromFile(orders_);
	}
	
	//Singleton
	public static OrdersRepository getInstance() {
		if (instance_ == null) {
			try {
				instance_ = new OrdersRepository();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance_;
	}
	
	public static void resetInstance() {
		instance_ = null;
		orders_ = null;
	}
	
	public Map<Integer, Order> getOrders(){
		return orders_;
		
	}
	
	public void setOrders(Map<Integer, Order> orders_) {
		OrdersRepository.orders_ = orders_;
	}
	
}

