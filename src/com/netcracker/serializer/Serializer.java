package com.netcracker.serializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 
 * @author acersanya
 *	Serialization for Entitites 
 */
public class Serializer {

	/**
	 * Write object to binary file
	 * @param object
	 * @param path
	 */
	public static void writeSerialize(Object object, String path) {
		try (FileOutputStream out = new FileOutputStream(path)) {
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read object from binary file
	 * @param path
	 * @return
	 */
	public	static Object readSerialize(String path){
		Object object = null;
		try(FileInputStream input = new FileInputStream(path)) {
			ObjectInputStream ois = new ObjectInputStream(input);
			try {
				object = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
}
