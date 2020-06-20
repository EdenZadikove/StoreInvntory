package view;

import java.util.ArrayList;

import controller.Controller;

public enum ProductsEnum {
	Gloves, Coat, Scarf, Swimsuit, Tshirt, Dress, values;
	
	private Controller controller_;
	
	ProductsEnum() {
		controller_ = new Controller();
	}
	
	
	public ArrayList<String> getProductsDetails(String product) {
		return controller_.getProductDetails(product);
	}
}
