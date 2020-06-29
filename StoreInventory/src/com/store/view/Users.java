package com.store.view;

import java.io.IOException;

public class Users{
	public void showMenu() throws IOException {
		System.out.println("\nWhich action would you like to do?\n");
		System.out.println("Create a new order     ========> 1");
		System.out.println("Cancel existing order  ========> 2");
		System.out.println("Back to main menu  	   ========> 3");	
	}
}
