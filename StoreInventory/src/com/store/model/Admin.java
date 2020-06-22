package com.store.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Admin extends User {

	public Admin(String userName, int passwrod, String phoneNumber, String email) throws IOException {
		super(userName, passwrod, phoneNumber, email, 1);
	}
}
