package com.store.model.entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order implements Serializable {
	private int orderId;
	private String itemName;
	private int quantity;
	private String orderStatus;
	private String orderDate;
	
	public Order(int orderId, String itemName, int quantity) {
		this.orderId = orderId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.orderStatus = "pending";
		this.orderDate = setFormatDate();
	}

	
	public String getItemName() {
		return itemName;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public int getOrderId() {
		return orderId;
	}
	
	
	public String getOrderDate() {
		return orderDate;
	}

	private String setFormatDate() {
		LocalDateTime  orderDate = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
		return orderDate.format(myFormatObj);
	}

	@Override
	public String toString() {
		return orderId + "::" + itemName + "::" +
				quantity + "::" + orderStatus + "::" + orderDate;
	}
	
}
