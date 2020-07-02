package com.store.view;

import java.util.ArrayList;
import com.store.controller.StoreController;

public enum ProductsEnum {
	Gloves, Coat, Scarf, Swimsuit, Tshirt, Dress, values;
	
	private StoreController storeController_;
	
	ProductsEnum() {
		storeController_ = new StoreController();
	}
	
	
	public ArrayList<String> getProductsDetails(String product) {
		return storeController_.getProductDetails(product);
	}
}
