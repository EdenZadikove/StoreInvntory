package com.store.view;

public class MenuFactory {
	
	public Menu createMenu(int userType) {

		switch(userType) {
		case 1:
			return new AdminMenu();
		case 2:
			return new SellerMenu();
		case 3:
			return new SupplierMenu();
		default:
			return null;
		}
	}
}
