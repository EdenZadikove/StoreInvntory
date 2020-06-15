package model;
import java.io.Serializable;

public class Order implements Serializable {
	private int orderId;
	private String itemName;
	private int quantity;
	private String orderStatus;
	
	public Order(int orderId, String itemName, int quantity) {
		this.orderId = orderId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.orderStatus = "pending";
	
	}

	
	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
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


	@Override
	public String toString() {
		return orderId + "::" + itemName + "::" +
				quantity + "::" + orderStatus;
	}
	
}
