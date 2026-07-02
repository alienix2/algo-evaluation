package com.matteo.projects.algo_evaluation;

public class BubbleSort implements SortingAlgorithm {
	@Override
	public Integer[] sorted(Integer[] array) {
		if (array == null) {
			return new Integer[0];
		}
		boolean swapped = true;
		while (swapped) {
			swapped = false;
			for (int j = 0; j < array.length - 1; j++) {
				if (array[j] > array[j + 1]) {
					Integer temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					swapped = true;
				}
			}
		}
		return array;
	}
}
