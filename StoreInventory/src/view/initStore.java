package view;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import model.Product;
import model.StoreDB;

public class initStore {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		StoreDB  storeDB_ = StoreDB.getInstance();
		
		 Map<String, Product> products_ = new Hashtable <String, Product>();
		
		Product p1 = new Product("Short-pants", "Summer", 20, 10);
		Product p2 = new Product("T-shirt", "Summer", 30, 20);
		Product p3 = new Product("Swimsuit", "Summer", 40, 30);
		Product p4 = new Product("Coat", "Winter", 50, 40);
		Product p5 = new Product("Gloves", "Winter", 60, 50);
		Product p6 = new Product("Scarf", "Winter", 70, 60);
		
	
		//products_ = storeDB_.getProducts();
		
		products_.put(p1.getItemName(), p1);
		products_.put(p2.getItemName(), p1);
		products_.put(p3.getItemName(), p1);
		products_.put(p4.getItemName(), p1);
		products_.put(p5.getItemName(), p1);
		products_.put(p6.getItemName(), p1);
		

		storeDB_.writeToFile(products_);
	}
	
	
}
