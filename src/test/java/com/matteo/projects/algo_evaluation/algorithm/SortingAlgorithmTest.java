package com.matteo.projects.algo_evaluation.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SortingAlgorithmTest {

	@Test
	public void sortingAlgorithmTest() {
		SortingAlgorithm algorithm = new BubbleSort();
		assertThat(algorithm.sorted(new Integer[]{2, 1})).containsExactly(1, 2);
	}

}
