package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public abstract class FileManager<E> {
	
	private File file = null;
	
	public FileManager(String path) {
		file  = new File(path);
	}
	
	private boolean isFileExsits() {
		return file.exists();
	}

	public E readFromFile(E element) throws IOException 
	{
		if(!isFileExsits()) {
			return element;
		}
		
		try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file))){
			element = (E)oi.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return element;
	}
	
	public void writeToFile(E element) throws IOException {
		file.delete();
		file.createNewFile(); 
		try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file))) {
			o.writeObject(element);
		}
	}
}

