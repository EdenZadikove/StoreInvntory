package com.store.model.entities;

import java.io.Serializable;
import java.util.Map;

public class Store implements Serializable {
	private String storeName;
	private String address;
	private User manager;
	private Map<String, Product> productsMap;
	
	public Store(String storeName, String address, User manager, Map<String, Product> productsMap) {
		super();
		this.storeName = storeName;
		this.address = address;
		this.manager = manager;
		this.productsMap = productsMap;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Map<String, Product> getProductsMap() {
		return productsMap;
	}

	public void setProductsMap(Map<String, Product> productsMap) {
		this.productsMap = productsMap;
	}
	
	
}
