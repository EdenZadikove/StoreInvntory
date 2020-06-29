package com.store.controller;

import java.util.ArrayList;

import com.store.model.Model;


public class Controller {
	private Model model_ = null;
	
	public Controller() {
		 model_ = new Model(); 
	}
	
	public ArrayList<String> getProductDetails(String product) {
		return model_.getProductDetails(product);
	}

}
