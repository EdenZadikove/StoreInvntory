package model;

import java.io.Serializable;

public class Product implements Serializable {
	private String itemName;
	private String session;
	private int quantity;
	private double price;
	
	public Product( String itemName, String session, int quantity, double price) {
		//super();
		this.itemName = itemName;
		this.session = session;
		this.quantity = quantity; 	//
		this.price = price;	///
	}

	public String getItemName() {
		return itemName;
	}

	public String getSession() {
		return session;
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
		return itemName + "::" + session + "::" +
				quantity + "::" + price;
	}
}
