package controller;

import java.io.IOException;
import java.util.Map;

import model.Model;

public class StoreController {
	private Model model_;
	
	public StoreController() throws IOException {
		model_ = new Model();
	}

	public Map<String, String> getProducts() throws IOException{
		return model_.getProducts();
	}
	
	public String editPrice(String itemName, double price) {
		return model_.editPrice(itemName, price);
	}
	
	public void saveToFile() throws IOException {
		model_.saveToFile();
	}
	
	public String removeProduct(String itemName) {
		return model_.removeProduct(itemName);
	}
}
