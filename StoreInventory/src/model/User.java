package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;

public abstract class User implements Serializable{
	
	private String userName;
	private int password;
	private String phoneNumber;
	private String email;
	private int userType; //1 = admin, 2 = user, 3 = supplier
	protected static OrdersDB ordersDB_ = null;
	protected Map<Integer, Order> orders_ = null; 
	protected static StoreDB storeDB_ = null;
	protected Map<String, Product> products_ = null; 
	
	public User(String userName, int password, String phoneNumber, String email, int userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.userType = userType;
		
		ordersDB_ = OrdersDB.getInstance();
		orders_ = ordersDB_.getOrders();
		
		storeDB_ = StoreDB.getInstance();
		products_ = storeDB_.getProducts();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getUserType()
	{
		return userType;	
	}
	
	public void setUserType(int userType)
	{
		this.userType = userType;
	}
	
	public String toString()
	{
		return userName + "::" + password + "::" +
				phoneNumber + "::" + email + "::" + userType;
	}
	
	public boolean isOrderExists(int orderId) {
		return orders_.get(orderId) != null;
	}
	
	
	public int itemsCounterByFilter(String filter) {
		int itemsCounter = 0;
		if(filter.equals("all"))
			return orders_.size();
		for (Map.Entry<Integer, Order> entry : orders_.entrySet()) 
			if(entry.getValue().getOrderStatus().equals(filter)) itemsCounter++;

		return itemsCounter;
	}
	
	public Map<String, String> getProducts(){
		
		Map<String, String> productsMap = new Hashtable<String, String>();
		for (Entry<String, Product> entry : products_.entrySet()) {
			String row = "ItemName:" + entry.getValue().getItemName() + ";" +
						 "Session:" + entry.getValue().getSession() + ";" +
						 "Quantity:" + entry.getValue().getQuantity() + ";" +
						 "Price:" +  entry.getValue().getPrice() + ";";
			productsMap.put(entry.getValue().getItemName(), row);
		}
		return productsMap;
	}
	
	public ArrayList<String> getProductDetails(String product) {
		ArrayList <String> details = new ArrayList<String>();
		Product p = products_.get(product);
		details.add(p.getItemName());
		details.add(p.getSession());
		details.add(String.valueOf(p.getPrice()));
		details.add(String.valueOf(p.getQuantity()));

		return details;
		
	}

	protected void logOut() {
		 UsersDB.resetInstance();
		 StoreDB.resetInstance();
		 OrdersDB.resetInstance();
	}
	
	public void saveToFile() throws IOException {
		ordersDB_.saveToFile();
		storeDB_.saveToFile();
	}
	
	 
	public abstract ArrayList<String> getOrders();
}
