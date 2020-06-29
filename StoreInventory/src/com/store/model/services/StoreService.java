package com.store.model.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import com.store.model.database.StoreDB;
import com.store.model.entities.Product;
import com.store.model.entities.Store;

public class StoreService {
	private static StoreDB storeDB_ = null;
	private Store store_;
	private Map<String, Product> defaultProductDetails_;

	public StoreService() {
		storeDB_ = StoreDB.getInstance();
		products_ = storeDB_.getProducts();
		initDefaultProductDetails();
	}
	
	private void initDefaultProductDetails(){
		defaultProductDetails_ = new Hashtable<String, Product>();
		Product p1 = new Product("Gloves", "Winter", 0, 10);
		Product p2 = new Product("Coat", "Winter", 0, 10);
		Product p3 = new Product("Scarf", "Winter", 0, 10);
		Product p4 = new Product("Swimsuit", "Summer", 0, 10);
		Product p5 = new Product("Dress", "Summer", 0, 10);
		Product p6 = new Product("Tshirt", "Summer", 0, 10);
		
		defaultProductDetails_.put(p1.getItemName(), p1);
		defaultProductDetails_.put(p2.getItemName(), p2);
		defaultProductDetails_.put(p3.getItemName(), p3);
		defaultProductDetails_.put(p4.getItemName(), p4);
		defaultProductDetails_.put(p5.getItemName(), p5);
		defaultProductDetails_.put(p6.getItemName(), p6);
	}
	
	
	//convert Map of objects to map of String. show store inventory in the view package
	public Map<String, String> getProducts(int userType){
		Map<String, String> productsMap = new Hashtable<String, String>(); 
		
		for (Entry<String, Product> entry : store_.getProductsMap().entrySet()) {
			String row = "ItemName:" + entry.getValue().getItemName() + ";" +
						 "Season:" + entry.getValue().getSeason() + ";" +
						 "Quantity:" + entry.getValue().getQuantity() + ";" +
						 "Price:" +  entry.getValue().getPrice() + ";";
			productsMap.put(entry.getValue().getItemName(), row);
		}
		return productsMap;
	}
	
	public ArrayList<String> getProductDetails(String product) {
		ArrayList <String> details = new ArrayList<String>();
		Product p = store_.getProductsMap().get(product);
		if(p == null) //product does not exists
			p = defaultProductDetails_.get(product); //initialize with default product
		details.add(p.getItemName());
		details.add(p.getSeason());
		details.add(String.valueOf(p.getPrice())); //convert double to String
		details.add(String.valueOf(p.getQuantity()));
		return details;
	}
	
	public boolean editPrice(String itemName, double price) {
		Product product  = store_.getProductsMap().get(itemName);
		if(product == null)
			return false;
		//double oldPrice = product.getPrice();
		store_.getProductsMap().get(itemName).setPrice(price);
		return true;
		
		//return (itemName + " price successfully update from: " + oldPrice + "$ to " + product.getPrice() + "$");
	}
	
	public boolean removeProduct(String itemName) {
		store_.getProductsMap().remove(itemName);
		return true;
		
		//return itemName + " successfully removed.";
	}
		
	
	public void addProductToStoreInventory(String itemName, int quantity) throws IOException {
		if(!isOrderExists(itemName)) { 		//product does not exists in the store yet
			Product p = defaultProductDetails_.get(itemName); //create a new object
			store_.getProductsMap().put(itemName, p);
		}
		int newQuantity = store_.getProductsMap().get(itemName).getQuantity() + quantity; //update quantity
		store_.getProductsMap().get(itemName).setQuantity(newQuantity); //set new quantity
		saveToFile();
	}
	
	private boolean isOrderExists(String itemName) {
		return store_.getProductsMap().get(itemName) != null;
	}
	
	public void saveToFile() throws IOException {
		storeDB_.writeToFile(store_);
	}
	
}
