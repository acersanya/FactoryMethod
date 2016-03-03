package com.netcracker.sort;

public class ModelView  {

	/**
	 * Prints array of classes which implement Printable interface
	 * @param Printable object
	 */
	public static <T> void print(Printable obj) {
	 T[] array = (T[])obj.getArray();
		for (T i : array) {
			System.out.print(i + " ");
		}
	}

	public static void printTime(long start, long finish){
		System.out.println("Algorith time:"+((double) finish-start) / 1000000000.0);
	}
	
	
	public  static void printMessage(String message){
		System.out.println(message);
	}

}
