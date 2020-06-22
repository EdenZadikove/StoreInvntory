package com.store.view;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import com.store.controller.StoreController;

public abstract class Store {
	protected ViewFunctions viewFunctions_; 
	protected StoreController storeController_;
	protected Map<String, String> productsMap_ = new Hashtable<String, String>();
	protected Scanner scanner_;

	public Store() throws IOException {
		super();
		viewFunctions_ = ViewFunctions.getInstance();
		storeController_ = new StoreController();
		getProducts();
		scanner_ = new Scanner(System.in);
	}
	
	protected void showProductsTable(int isFiltered, String tableHeader, String emptyTableHeader) throws IOException {
		getProducts();
		if(productsMap_.size() == 0) {
			System.out.println(emptyTableHeader);
		} else {
			System.out.println(tableHeader);
			System.out.println();
			System.out.println("| Item Name           | Season               | Quantity             | Price                |");
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
	
	protected void getProducts() throws IOException {
		productsMap_ = storeController_.getProducts();
	}	
}