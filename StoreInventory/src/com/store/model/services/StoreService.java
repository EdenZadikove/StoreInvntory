package com.store.model.services;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import com.store.model.entities.Product;
import com.store.model.entities.Store;
import com.store.model.repository.StoreRepository;

public class StoreService {
	private StoreRepository storeRepository_;
	private Map<String, Product> defaultProductDetails_;

	public StoreService(){
		storeRepository_ = StoreRepository.getInstance();
		initStore();
		initDefaultProductDetails();
	}
	
	//convert Map of objects to map of String. show store inventory in the view package
	public Map<String, String> getProducts(){
		Map<String, String> productsMap = new Hashtable<String, String>(); 
		for (Entry<String, Product> entry : storeRepository_.getStore().getProductsMap().entrySet()) {
			String row = "ItemName:" + entry.getValue().getItemName() + ";" +
						 "Season:" + entry.getValue().getSeason() + ";" +
						 "Quantity:" + entry.getValue().getQuantity() + ";" +
						 "Price:" +  entry.getValue().getPrice() + ";";
			productsMap.put(entry.getValue().getItemName(), row);
		}
		return productsMap;
	}
	
	public ArrayList<String> getProductDetails(String productName) {
		ArrayList <String> details = new ArrayList<String>();
		Product p = storeRepository_.getStore().getProductsMap().get(productName);
		if(p == null) //product does not exists
			p = defaultProductDetails_.get(productName); //initialize with default product
		details.add(p.getItemName());
		details.add(p.getSeason());
		details.add(String.valueOf(p.getPrice())); //convert double to String
		details.add(String.valueOf(p.getQuantity()));
		return details;
	}
	
	public boolean editPrice(String itemName, double price) {
		Product product  = storeRepository_.getStore().getProductsMap().get(itemName);
		if(product == null)
			return false;
		storeRepository_.getStore().getProductsMap().get(itemName).setPrice(price);
		return true;
	}
	
	public boolean removeProduct(String itemName) {
		if(storeRepository_.getStore().getProductsMap().remove(itemName) != null) {
			return true;
		}
		return false; //item does not exists
	}	
	
	public void addProductToStoreInventory(String itemName, int quantity) {
		if(!isProductExists(itemName)) { 		//product does not exists in the store yet
			Product p = defaultProductDetails_.get(itemName); //create a new object
			storeRepository_.getStore().getProductsMap().put(itemName, p);
		}
		int newQuantity = storeRepository_.getStore().getProductsMap().get(itemName).getQuantity() + quantity; //update quantity
		storeRepository_.getStore().getProductsMap().get(itemName).setQuantity(newQuantity); //set new quantity
	}
	
	public boolean isEmptyStore() {
		return storeRepository_.getStore().getProductsMap().size() == 0;
	}
	
	public boolean isProductExists(String productName) {
		return storeRepository_.getStore().getProductsMap().get(productName) != null;
	}
	
	public void saveToFileStore(){
		storeRepository_.saveToFile();
	}
	
	private void initStore(){
		Store store = storeRepository_.getStore();
		if(store == null) {
			Map<String, Product> products = new Hashtable<String, Product>();
			store = new Store("Best-Store-Ever", "Holon", "Eden Zadikove", products);
			storeRepository_.setStore(store);
		}
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
}
