package com.store.model.entities;

import java.io.Serializable;
import java.util.Map;

public class Store implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6731452110436423748L;
	private String storeName;
	private String address;
	private String managerName;
	private Map<String, Product> products_;
	
	public Store(String storeName, String address, String managerName, Map<String, Product> products_) {
		super();
		this.storeName = storeName;
		this.address = address;
		this.managerName = managerName;
		this.products_ = products_;
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Map<String, Product> getProductsMap() {
		return products_;
	}

	public void setProductsMap(Map<String, Product> productsMap) {
		this.products_ = productsMap;
	}
}
