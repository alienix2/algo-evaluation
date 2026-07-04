package com.matteo.projects.algo_evaluation.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SortingAlgorithmRegistryTest {
	
	@Test
	 public void testGetUnregisteredAlgorithmReturnsNull() {
	     SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
	     assertThat(registry.get("Unknown")).isNull();
	 }

	@Test
	 public void testGetRegisteredAlgorithm() {
	     SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
	     BubbleSort bubbleSort = new BubbleSort();
	     SelectionSort selectionSort = new SelectionSort();
	     registry.register("BubbleSort", bubbleSort);
	     registry.register("SelectionSort", selectionSort);
	     assertThat(registry.get("BubbleSort")).isEqualTo(bubbleSort);
	 }

}
