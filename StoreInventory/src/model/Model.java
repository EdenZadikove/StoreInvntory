package model;
import java.util.ArrayList;

import java.io.IOException;

public class Model    {
	
	private static User user_= null;  
	
	public Model() {}
	
	public int signIn(String email, int password) throws IOException {
		LoginRepository loginRepository = new LoginRepository();
		User user = loginRepository.validateData(email, password);
		
	//create a new user with design pattern- factory
		if(user != null) {
			createUser(user);
			return user.getUserType();
		}
		return 0;
	}
	
	public String getUserName() {
		return user_.getUserName();
	}
	
	private void createUser(User user) throws IOException {
		UsersFactory usersFactory = new UsersFactory();
		setUser(usersFactory.getUser(user));
	}
	
	public void setUser(User user) {
		this.user_ = user;
	}
	

	

	/*ORDERS Functions*/
	
	public int cretaeOrder(String itemName, int quantity) throws IOException {
		return ((Admin)user_).cretaeOrder(itemName, quantity);
	}
	
	public String cancelOrder(int orderId) {
		return ((Admin)user_).cancelOrder(orderId);
	}
	
	public String deleteOrder(int orderId) {
		return ((Admin)user_).deleteOrder(orderId);
	}
	
	public void saveToFile_orders() throws IOException {
		((Admin)user_).saveToFile();
	}

	public String editOrder(int orderId, int quantity) {
		return ((Admin)user_).editOrder(orderId, quantity);
	}
	
	public int getOrdersSize() {
		return ((Admin)user_).getOrdersSize();
	}
	
	//Supplier
	public ArrayList<String> showOrdersTable(int isFiltered) throws IOException{
		if(isFiltered == 0) return ((Admin)user_).showOrdersTable();
		return ((Supplier)user_).showOrdersTable();
	}
	
	public int getPendingOrdersSize() {
		return ((Supplier)user_).getPendingOrdersSize();
	}
	
	public void saveToFile_ordersSupplier() throws IOException {
		((Supplier)user_).saveToFileOrders();
	}
	
	public String changeOrderStatus(int orderId, String action) {
		return ((Supplier)user_).changeOrderStatus(orderId, action);
	}
	
}
