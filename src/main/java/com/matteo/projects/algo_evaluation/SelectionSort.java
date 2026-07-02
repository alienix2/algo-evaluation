package com.matteo.projects.algo_evaluation;

public class SelectionSort implements SortingAlgorithm {

	@Override
	public Integer[] sorted(Integer[] array) {
		if (array == null) {
			return new Integer[0];
		}
		for (int i = 0; i < array.length; i++) {
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < array[minIndex]) {
					minIndex = j;
				}
			}
			Integer temp = array[minIndex];
			array[minIndex] = array[i];
			array[i] = temp;
		}
		return array;
	}

}
