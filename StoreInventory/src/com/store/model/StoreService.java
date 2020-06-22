package com.store.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class StoreService {
	private static StoreDB storeDB_ = null;
	private Map<String, Product> products_ = null; 
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
	
	public Map<String, String> getProducts(int userType){ //admin, seller
		Map<String, String> productsMap = new Hashtable<String, String>(); 
		
		for (Entry<String, Product> entry : products_.entrySet()) {
			String row = "ItemName:" + entry.getValue().getItemName() + ";" +
						 "Season:" + entry.getValue().getSeason() + ";" +
						 "Quantity:" + entry.getValue().getQuantity() + ";" +
						 "Price:" +  entry.getValue().getPrice() + ";";
			productsMap.put(entry.getValue().getItemName(), row);
		}
		return productsMap;
	}
	
	public ArrayList<String> getProductDetails(String product) { //admin
		ArrayList <String> details = new ArrayList<String>();
		Product p = products_.get(product);
		if(p == null) //product does not exists
			p = defaultProductDetails_.get(product);
		details.add(p.getItemName());
		details.add(p.getSeason());
		details.add(String.valueOf(p.getPrice()));
		details.add(String.valueOf(p.getQuantity()));
		return details;
	}
	
	public String editPrice(String itemName, double price) { //admin
		Product product  = products_.get(itemName);
		double oldPrice = product.getPrice();
		products_.get(itemName).setPrice(price);
		return (itemName + " price successfully update from: " + oldPrice + "$ to " + product.getPrice() + "$");
	}
	
	public String removeProduct(String itemName) { //admin
		products_.remove(itemName);
		return itemName + " successfully removed.";
	}
		
	
	public void updateStoreInventory(String itemName, int quantity) {
		if(!isOrderExists(itemName)) { //product is not exists in the store yet
			Product p = defaultProductDetails_.get(itemName);
			products_.put(itemName, p);
		}
		int newQuantity = products_.get(itemName).getQuantity() + quantity;
		products_.get(itemName).setQuantity(newQuantity); //set new quantity
	}
	
	private boolean isOrderExists(String itemName) {
		return products_.get(itemName) != null;
	}
	
	public void saveToFile() throws IOException { //admin
		storeDB_.writeToFile(products_);
	}
	
}
