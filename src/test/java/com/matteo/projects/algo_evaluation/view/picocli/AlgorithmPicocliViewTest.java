package com.matteo.projects.algo_evaluation.view.picocli;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

public class AlgorithmPicocliViewTest {

	private StringWriter outputStream;
	private AlgorithmPicocliView view;

	@Before
	public void setup() {
		outputStream = new StringWriter();
		view = new AlgorithmPicocliView(new PrintWriter(outputStream));
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

	@Test
	public void testShowAlgorithmError() {
		view.showAlgorithmError("Algorithm not found", null);
		assertThat(outputStream.toString()).contains("ERROR: Algorithm not found");
	}

	@Test
	public void testShowDatasetError() {
		view.showDatasetError("Dataset not found", null);
		assertThat(outputStream.toString()).contains("ERROR: Dataset not found");
	}

	@Test
	public void testShowRunError() {
		view.showRunError("Run not found", null);
		assertThat(outputStream.toString()).contains("ERROR: Run not found");
	}
}
