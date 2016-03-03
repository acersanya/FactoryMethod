package com.netcracker.sort;



public class QuickSort <T extends Comparable<T>> implements Printable, Sortable {

	private T[] array;

	public QuickSort(){
		
	}
	
	
	public QuickSort(T[] array) {
		this.array = array;
		sort();
	}

	/**
	 * Array setter
	 */
	public void setArray(T[] array) {
		this.array = array;
	}
	
	/**
	 * calling quick sort method here
	 */
	


	
	@Override

	public void sort() {
		quickSort(0, array.length - 1);
	}
	
	/**
	 * Quick sort algorithm
	 * @param low
	 * @param high
	 */

	public void quickSort(int low, int high) {
		int i = low;
		int j = high;
		T median = array[low + (high - low) / 2];

		while (i <= j) {
			while (array[i].compareTo(median) < 0) {
				i++;
			}
			while (array[j].compareTo(median) > 0) {
				j--;
			}

			if (i <= j) {
				swap(i, j);
				i++;
				j--;
			}
		}

		if (low < j) {
			quickSort(low, j);
		}
		if (i < high) {
			quickSort(i, high);
		}

	}
	/**
	 * Swap two elements using third
	 * @param i first element
	 * @param j second element
	 */

	private void swap(int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * Array getter
	 */
	@Override
	public T[] getArray() {
		return array;
	}

}
