package com.matteo.projects.algo_evaluation.view.picocli;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.controller.AlgorithmController;
import com.matteo.projects.algo_evaluation.controller.DatasetController;
import com.matteo.projects.algo_evaluation.controller.RunController;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;

import picocli.CommandLine;

public class NewRunCommandTest {

	@Mock
	private AlgorithmController algorithmController;
	@Mock
	private DatasetController datasetController;
	@Mock
	private RunController runController;
	@Mock
	private AlgorithmPicocliApp mockApp;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		when(mockApp.getAlgorithmController()).thenReturn(algorithmController);
		when(mockApp.getDatasetController()).thenReturn(datasetController);
		when(mockApp.getRunController()).thenReturn(runController);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testCallExecutesRun() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Dataset dataset = new Dataset("2", "dataset2", Arrays.asList(3, 1, 2));
		when(algorithmController.findById("1")).thenReturn(algorithm);
		when(datasetController.findById("2")).thenReturn(dataset);

		NewRunCommand cmd = new NewRunCommand();
		cmd.parent = mockApp;
		new CommandLine(cmd).execute("--algorithm-id=1", "--dataset-id=2");

		verify(runController).newRun(algorithm, dataset);
	}

	@Test
	public void testCallShowsAllRunsAfterExecution() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Dataset dataset = new Dataset("2", "dataset2", Arrays.asList(3, 1, 2));
		when(algorithmController.findById("1")).thenReturn(algorithm);
		when(datasetController.findById("2")).thenReturn(dataset);

		NewRunCommand cmd = new NewRunCommand();
		cmd.parent = mockApp;
		new CommandLine(cmd).execute("--algorithm-id=1", "--dataset-id=2");

		InOrder inOrder = inOrder(runController);
		inOrder.verify(runController).newRun(algorithm, dataset);
		inOrder.verify(runController).allRuns();
	}
}
