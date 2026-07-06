package com.matteo.projects.algo_evaluation.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SelectionSortTest {
	
	@Test
	public void nullArrayTest() {
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = null;
		assertThat(selectionSort.sorted(array)).isEmpty();
	}
	
	@Test
	public void emptyArrayTest() {
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = {};
		assertThat(selectionSort.sorted(array)).isEmpty();
	}
	
	@Test
	public void singleElementArrayTest() {
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = {1};
		assertThat(selectionSort.sorted(array)).containsExactly(1);
	}
	
	@Test
	public void twoElementsOrderedArrayTest() {
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = {1, 2};
		assertThat(selectionSort.sorted(array)).containsExactly(1, 2);
	}
	
	@Test
	public void testDuplicateElementsArrayTest() {
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = {3, 1, 2, 3, 1};
		assertThat(selectionSort.sorted(array)).containsExactly(1, 1, 2, 3, 3);
	}
	
	@Test
	public void multipleElementsUnorderedArrayTest() {
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = {5, 3, 8, 1, 2};
		assertThat(selectionSort.sorted(array)).containsExactly(1, 2, 3, 5, 8);
	}
	
	@Test
	public void isStableTest() {
		Integer a = 130;
		Integer b = 130;
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = {a, 2, b};
		Integer[] sortedArray = bubbleSort.sorted(array);
		assertThat(sortedArray[1]).isSameAs(a);
		assertThat(sortedArray[2]).isSameAs(b);
	}

}
