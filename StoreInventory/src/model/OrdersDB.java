package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;


public class OrdersDB extends FileManager<Object> {

	private static String path_ = "C:\\Users\\97250\\eclipse-workspace\\store-inventory-project---java\\StoreInventory\\files\\orders.txt";
	private static Map<Integer, Order> orders_ = new Hashtable <Integer, Order>();
	private static OrdersDB instance = null;
	
	
	@SuppressWarnings("unchecked")
	private OrdersDB () throws IOException {
		super(path_);
		orders_ = (Map<Integer, Order>) readFromFile(orders_);
	}
	
	//Singletone
	public static OrdersDB getInstance() {
		if (instance == null) {
			try {
				instance = new OrdersDB();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public Map<Integer, Order> getOrders(){
		return orders_;
	}
	
	public void setOrders(Map<Integer, Order> orders_2) {
		this.orders_ = orders_2;
	}
	
	public void saveToFile() throws IOException {
		writeToFile(orders_);
	}
}

