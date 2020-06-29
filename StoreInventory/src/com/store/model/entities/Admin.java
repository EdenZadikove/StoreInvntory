package com.store.model.entities;

import java.io.IOException;

public class Admin extends User {

	public Admin(String userName, int passwrod, String phoneNumber, String email) throws IOException {
		super(userName, passwrod, phoneNumber, email, 1);
	}
}
