package com.store.model.database;

import java.io.IOException;
import com.store.model.entities.Store;

public class StoreRepository extends FileManager<Object> {
	
	private static String path_ = "..\\StoreInventory\\files\\store.txt";
	private static Store store_ ;
	private static StoreRepository instance_ = null;
	
	private StoreRepository() throws IOException {
		super(path_);
		store_ = (Store)readFromFile(store_);
	}
	
	//Singleton
	public static StoreRepository getInstance() {
		if (instance_ == null) {
			try {
				instance_ = new StoreRepository();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return instance_;
	}

	public Store getStore(){
		return store_;
	}
	
	public static void resetInstance() {
		instance_ = null;
		store_ = null;
	}
}