package model;

import java.io.IOException;
import java.util.Map;

public class StoreDB extends FileManager<Object> {
	
	private static String path_ = "C:\\Users\\97250\\eclipse-workspace\\store-inventory-project---java\\StoreInventory\\files\\users.txt";
	private Map <String, Product> products_ = new Hashtable<String, Product>();
	private static StoreDB instance = null;
	
	private StoreDB() {
		super(path_);
		products_  = (Map<String, Product>)readFromFile(products_);
	}
	
	//Singletone
	public static StoreDB getInstance() {
		if (instance == null) {
			try {
				instance = new StoreDB();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	
	
	public Map<String, Product> getProducts(){
		return products_;
	}
}