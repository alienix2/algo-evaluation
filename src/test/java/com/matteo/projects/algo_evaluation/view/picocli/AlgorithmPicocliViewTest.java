package com.matteo.projects.algo_evaluation.view.picocli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

public class AlgorithmPicocliViewTest {

	private ByteArrayOutputStream outputStream;
	private AlgorithmPicocliView view;

	@Before
	public void setup() {
		outputStream = new ByteArrayOutputStream();
		view = new AlgorithmPicocliView(new PrintStream(outputStream));
	}

	@Test
	public void testShowAllAlgorithms() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		view.showAllAlgorithms(Arrays.asList(algorithm));
		assertThat(outputStream.toString()).contains("ID - Name").contains("1 - BubbleSort");
	}

	@Test
	public void testShowAllDatasets() {
		Dataset dataset = new Dataset("1", "dataset1", Arrays.asList(1, 2, 3));
		view.showAllDatasets(Arrays.asList(dataset));
		assertThat(outputStream.toString()).contains("ID - Name - Integers").contains("1 - dataset1 - [1, 2, 3]");
	}

	@Test
	public void testShowAllRuns() {
		Run run = new Run("1", "1", "1", 100);
		view.showAllRuns(Arrays.asList(run));
		assertThat(outputStream.toString()).contains("ID - AlgorithmId - DatasetId - Execution Time (ms)")
				.contains("1 - 1 - 1 - 100");
	}

	@Test
	public void testRunAdded() {
		Run run = new Run("1", "1", "1", 100);
		view.runAdded(run);
		assertThat(outputStream.toString()).contains("Run added: 1");
	}
}
