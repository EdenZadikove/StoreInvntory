package com.store.model.repository;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.entities.Order;

public class OrdersRepository{

	private static String path_ = "..\\StoreInventory\\files\\orders.txt";
	private  Map<Integer, Order> orders_ = new Hashtable<Integer, Order>();
	private static OrdersRepository instance_ = null;
	private FileManager<Map<Integer, Order>> fileManager;
	
	private OrdersRepository () throws IOException {
		this.fileManager = new FileManager<>(path_);
		this.orders_ = this.fileManager.readFromFile(orders_);
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
	
	public void resetInstance() {
		instance_ = null;
		orders_ = null;
	}
	
	public Map<Integer, Order> getOrders(){
		return orders_;
		
	}
	
	public void setOrders(Map<Integer, Order> orders_) {
		this.orders_ = orders_;
	}
	
	public static void setPath_(String newPath) {
		path_ = newPath;
	}
	
	public void saveToFile(){
		fileManager.writeToFile(orders_);
	}
}

