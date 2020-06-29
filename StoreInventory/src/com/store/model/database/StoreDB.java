package com.store.model.database;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import com.store.model.entities.Product;
import com.store.model.entities.Store;

public class StoreDB extends FileManager<Object> {
	
	private static String path_ = "..\\StoreInventory\\files\\store.txt";
	private static Map<String, Store> stores_ = new Hashtable<String, Store>() ;
	//private static Map <String, Product> products_ = new Hashtable<String, Product>();
	private static StoreDB instance_ = null;
	
	@SuppressWarnings("unchecked")
	private StoreDB() throws IOException {
		super(path_);
		stores_ = (Map<String, Store>)readFromFile(stores_);
		
		//products_  = (Map<String, Product>)readFromFile(products_);
		initStore();
	}
	
	//Singletone
	public static StoreDB getInstance() {
		if (instance_ == null) {
			try {
				instance_ = new StoreDB();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance_;
	}

	public Map<String, Store> getStores(){
		return stores_;
	}
	
	public static void resetInstance() {
		instance_ = null;
		stores_ = null;
	}
	
	private void initStore() throws IOException {
		if(products_.size() == 0) {		
			Product p1 = new Product("Gloves", "Winter", 1, 50);
			Product p2 = new Product("Coat", "Winter", 1, 40);
			Product p3 = new Product("Scarf", "Winter", 1, 60);
			Product p4 = new Product("Swimsuit", "Summer", 1, 30);
			Product p5 = new Product("Dress", "Summer", 1, 10);
			Product p6 = new Product("Tshirt", "Summer", 1, 20);
			
			products_.put(p1.getItemName(), p1);
			products_.put(p2.getItemName(), p2);
			products_.put(p3.getItemName(), p3);
			products_.put(p4.getItemName(), p4);
			products_.put(p5.getItemName(), p5);
			products_.put(p6.getItemName(), p6);
			
			writeToFile(products_);
		}
	}
}