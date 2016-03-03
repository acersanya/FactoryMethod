package com.netcracker.sort;


import java.util.Arrays;


public class DefaultSort <T extends Comparable<T>> implements Printable, Sortable {

	private T[] array;

	public DefaultSort(){
		
	}
	

	public void setArray(T[] array) {
		this.array = array;
	}
	
	public DefaultSort(T[] array) {
		this.array = array;
		sort();
	}

	/**
	 * Using sort method from Arrays class
	 */
	@Override
	public void sort() {
		Arrays.sort(array);
	}

	@Override
	public T[] getArray() {
		return array;
	}

}
