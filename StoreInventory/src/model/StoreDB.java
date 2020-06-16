package model;

import java.io.IOException;
import java.util.Map;

public class StoreDB extends FileManager<Object> {
	
	private static String path_ = "..\\StoreInventory\\files\\store.txt";
	private static Map <String, Product> products_ = null;
	private static StoreDB instance_ = null;
	
	@SuppressWarnings("unchecked")
	private StoreDB() throws IOException {
		super(path_);
		products_  = (Map<String, Product>)readFromFile(products_);
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
	
	public void resetInstance() {
		instance_ = null;
		products_ = null;
	}
	
	public Map<String, Product> getProducts(){
		return products_;
	}
	
	public void saveToFile() throws IOException {
		writeToFile(products_);
	}
}