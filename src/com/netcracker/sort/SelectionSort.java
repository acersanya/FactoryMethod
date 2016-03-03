package com.netcracker.sort;


public class SelectionSort <T extends Comparable<T>> implements Printable, Sortable {

	private T[] array;

	public SelectionSort() {

	}

	public SelectionSort(T[] array) {
		 this.array = array;
		sort();
	}

	public void setArray(T[] array) {
		this.array = array;
	}

	/**
	 * Sorting algorithm using Selection sort
	 */
	@Override
	public void sort() {
		for (int i = 0; i < array.length - 1; i++) {
			T min = array[i];
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(min) < 0) {
					min = array[j];
					minIndex = j;
				}

			}
			swap(i, min, minIndex);
		}

	}

	/**
	 * Swap min element in array with current element at the beginning of the
	 * array
	 * 
	 * @param i
	 * @param min
	 * @param minIndex
	 */
	private void swap(int i, T min, int minIndex) {
		T temp = array[i];
		array[i] = min;
		array[minIndex] = temp;
	}


	@Override
	public T[] getArray() {
		return array;
	}

}
