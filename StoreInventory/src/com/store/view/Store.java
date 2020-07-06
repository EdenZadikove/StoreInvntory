package com.store.view;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import com.store.controller.StoreController;

public abstract class Store {
	protected ViewFunctions viewFunctions_; 
	protected StoreController storeController_;
	protected Scanner scanner_;

	public Store(){
		super();
		viewFunctions_ = new ViewFunctions();
		storeController_ = new StoreController();
		scanner_ = new Scanner(System.in);
	}
	
	protected void showProductsTable(String tableHeader, String emptyTableHeader) {
		Map<String, String> productsMap_ = getProducts();
		
		if(productsMap_.size() == 0) {
			System.out.println(emptyTableHeader);
		} else {
			System.out.println(tableHeader);
			System.out.println();
			System.out.println("| Item Name           | Season               | Quantity             | Price (per unit)     |");
			for (Entry<String, String> entry : productsMap_.entrySet()) {
				String ItemName = StringUtils.substringBetween(entry.getValue(), "ItemName:", ";");
				String Season = StringUtils.substringBetween(entry.getValue(), "Season:", ";");
				String Quantity = StringUtils.substringBetween(entry.getValue(), "Quantity:", ";");
				String Price = StringUtils.substringBetween(entry.getValue(), "Price:", ";");
				System.out.printf("| %-20s| %-20s | %-20s | %-20s |\n",ItemName, Season, Quantity, Price);
			}
		}
		System.out.println();
	}
	
	protected Map<String, String> getProducts() {
		return storeController_.getProducts();
	}	
	
	protected boolean isEmptyStore() {
		if(storeController_.isEmptyStore()) {
			System.out.println("! Action can not be taken. Store is empty.\n");
			System.out.println(viewFunctions_.getSeperator());
			return true;
		}
		return false;
	}
	
}
