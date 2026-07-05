package com.matteo.projects.algo_evaluation.view.picocli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.matteo.projects.algo_evaluation.model.Algorithm;

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
}
