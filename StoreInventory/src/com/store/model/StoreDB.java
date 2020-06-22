package com.store.model;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class StoreDB extends FileManager<Object> {
	
	private static String path_ = "..\\StoreInventory\\files\\store.txt";
	private static Map <String, Product> products_ = new Hashtable<String, Product>();
	private static StoreDB instance_ = null;
	
	@SuppressWarnings("unchecked")
	private StoreDB() throws IOException {
		super(path_);
		products_  = (Map<String, Product>)readFromFile(products_);
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

	public Map<String, Product> getProducts(){
		return products_;
	}
	
	public static void resetInstance() {
		instance_ = null;
		products_ = null;
	}
	
	private void initStore() throws IOException {
		if(products_.size() == 0) {		
			Product p1 = new Product("Gloves", "Winter", 60, 50);
			Product p2 = new Product("Coat", "Winter", 50, 40);
			Product p3 = new Product("Scarf", "Winter", 70, 60);
			Product p4 = new Product("Swimsuit", "Summer", 40, 30);
			Product p5 = new Product("Dress", "Summer", 20, 10);
			Product p6 = new Product("Tshirt", "Summer", 30, 20);
			
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