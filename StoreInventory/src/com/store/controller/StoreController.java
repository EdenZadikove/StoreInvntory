package com.store.controller;

import java.util.ArrayList;
import java.util.Map;

import com.store.model.services.StoreService;

public class StoreController {;
	private StoreService storeService_;
	
	public StoreController(){
		storeService_ = new StoreService();
	}

	public Map<String, String> getProducts(){
		return storeService_.getProducts();
	}
	
	public boolean editPrice(String itemName, double price, double oldPrice) throws Exception {
		//validate price
		if(price == oldPrice)
			throw new Exception("\n! " + itemName + " price is already " + price + ".");
		else if(price <= 0)
			throw new Exception("\n! " + itemName + " price must be more then 0");
		return storeService_.editPrice(itemName, price);
	}
	
	public boolean removeProduct(String itemName) {
		return storeService_.removeProduct(itemName);
	}
	
	public ArrayList<String> getProductDetails(String product) {
		return storeService_.getProductDetails(product);
	}

	public void saveToFileStore() {
		storeService_.saveToFileStore();
	}
}
