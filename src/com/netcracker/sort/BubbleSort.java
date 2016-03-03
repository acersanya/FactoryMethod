package com.netcracker.sort;
/**
 * 
 * @author acersanya
 *	Basic buble sort implementation 
 *	Generic is used
 * @param <T>
 */
public class BubbleSort<T extends Comparable<T>> implements Printable, Sortable {

	private T[] array;

	/**
	 * Getter
	 * 
	 * @return array
	 */
	public T[] getArray() {
		return array;
	}

	/**
	 * Setter
	 * 
	 * @param array
	 */
	public void setArray(T[] array) {
		this.array = array;
	}

	public BubbleSort() {

	}

	public BubbleSort(T[] array) {
		this.array = array;
	}

	/**
	 * Sorting array using bubble root Max element is stored at the and of array
	 */
	@Override
	public void sort() {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - i - 1; j++) {
				if (array[j].compareTo(array[j + 1]) > 0) {
					swap(j, j + 1);
				}
			}
		}

	}

	/**
	 * Swap i and j
	 * 
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * Sort is the same as bubble sort But max element is stored at the
	 * beginning of the array
	 */
	public void reverseSort() {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j].compareTo(array[j + 1]) < 0) {
					swap(j, j + 1);
				}
			}
		}
	}

}
