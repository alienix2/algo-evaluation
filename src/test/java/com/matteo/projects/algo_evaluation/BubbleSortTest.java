package com.matteo.projects.algo_evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BubbleSortTest {
	
	@Test
	public void nullArrayTest() {
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = null;
		assertThat(bubbleSort.sorted(array)).isNull();
	}
	
	@Test
	public void emptyArrayTest() {
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = {};
		assertThat(bubbleSort.sorted(array)).isEmpty();
	}
	
	@Test
	public void singleElementArrayTest() {
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = {1};
		assertThat(bubbleSort.sorted(array)).containsExactly(1);
	}
	
	@Test
	public void twoElementsOrderedArrayTest() {
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = {1, 2};
		assertThat(bubbleSort.sorted(array)).containsExactly(1, 2);
	}
	
	@Test
	public void testDuplicateElementsArrayTest() {
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = {3, 1, 2, 3, 1};
		assertThat(bubbleSort.sorted(array)).containsExactly(1, 1, 2, 3, 3);
	}
	
	@Test
	public void multipleElementsUnorderedArrayTest() {
		SortingAlgorithm bubbleSort = new BubbleSort();
		Integer[] array = {5, 3, 8, 1, 2};
		assertThat(bubbleSort.sorted(array)).containsExactly(1, 2, 3, 5, 8);
	}
	
	@Test
	public void isStableTest() {
		Integer a = 130;
		Integer b = 130;
		SortingAlgorithm selectionSort = new SelectionSort();
		Integer[] array = {a, 2, b};
		Integer[] sortedArray = selectionSort.sorted(array);
		assertThat(sortedArray[1]).isSameAs(a);
		assertThat(sortedArray[2]).isSameAs(b);
	}

}
