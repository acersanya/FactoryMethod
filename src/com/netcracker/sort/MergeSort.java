package com.netcracker.sort;

/**
 * 
 * @author acersanya
 *	Merge sort implementation
 * @param <T>
 */

public class MergeSort <T extends Comparable<T>> implements Printable, Sortable {

	private T[] array;
	private T[] temp;

	public MergeSort(T[] obj) {
	this.array = obj;
		sort();
	}
	
	public MergeSort(){
		
	}
	
	
	public void setArray(T[] array) {
		this.array = array;
	}


	@Override
	public void sort() {
		mergeSort(0, array.length - 1);
	}

	/**
	 * Check if low is smaller then high, if yes
	 * array is sorted 
	 * @param low array element
	 * @param high  array element 
	 */
	public void mergeSort(int low, int high) {
		if (low < high) {
			//middle array index
			int middle = low + (high - low) / 2;
			//sort left array side
			mergeSort(low, middle);
			//sort right array side
			mergeSort(middle + 1, high);
			//Combine both parts
			merge(low, middle, high);
		}
	}

	/**
	 * Copying smallest element from right or left part of the array 
	 * @param low
	 * @param middle
	 * @param high
	 */
	public void merge(int low, int middle, int high) {
		for (int i = low; i <= high; i++) {
			temp[i] = array[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;

		while (i <= middle && j <= high) {
			if (temp[i].compareTo(temp[j]) < 0) {
				array[k] = temp[i];
				i++;
			} else {
				array[k] = temp[j];
				j++;
			}
			k++;
		}

		while (i <= middle) {
			array[k] = temp[i];
			k++;
			i++;
		}
	}

	/**
	 * Getter
	 * @return sorted array
	 */
	@Override
	public T[] getArray() {
		return array;
	}

}
