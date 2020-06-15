package model;

import java.util.Map;

public class StoreDB extends FileManager<Object> {
	
	private static String path_ = "C:\\Users\\97250\\eclipse-workspace\\store-inventory-project---java\\StoreInventory\\files\\users.txt";
	private Map <String, Product> products_ = new Hashtable<String, Product>();
	
	public StoreDB() {
		super(path_);
		products_  = (Map<String, Product>)readFromFile(products_);
	}
	
	public Map<String, Product> getProducts(){
		return products_;
	}
}