package com.store.model.repository;

import java.io.IOException;

import com.store.model.entities.Store;

public class StoreRepository{
	
	private String path_ = "..\\StoreInventory\\files\\store.txt";
	private Store store_ ;
	private static StoreRepository instance_ = null;
	private FileManager<Store> fileManager; 
	
	private StoreRepository() throws IOException {
		this.fileManager = new FileManager<>(path_);
		store_ = fileManager.readFromFile(store_);
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
	
	public void setStore(Store store){
		this.store_ = store;
	}
	
	public void setPath_(String path) {
		this.path_ = path;
	}
	
	public void resetInstance() {
		instance_ = null;
		store_ = null;
	}
	
	public void saveToFile(){
		fileManager.writeToFile(store_);
	}
}