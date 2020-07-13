package com.store.model.entities;

import java.io.Serializable;

public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2502493345926536930L;
	private String itemName;
	private String season;
	private int quantity;
	private double price;
	
	public Product( String itemName, String season, int quantity, double price) {
		this.itemName = itemName;
		this.season = season;
		this.quantity = quantity;
		this.price = price;	
	}

	public String getItemName() {
		return itemName;
	}

	public String getSeason() {
		return season;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString()
	{
		return itemName + "::" + season + "::" + quantity + "::" + price;
	}
}
