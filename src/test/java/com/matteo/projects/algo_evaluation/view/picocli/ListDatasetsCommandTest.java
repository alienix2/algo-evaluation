package com.matteo.projects.algo_evaluation.view.picocli;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.controller.DatasetController;

public class ListDatasetsCommandTest {

	@Mock
	private DatasetController datasetController;
	
	@Mock
    private AlgorithmPicocliApp mockApp;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		when(mockApp.getDatasetController()).thenReturn(datasetController);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
		when(mockApp.getDatasetController()).thenReturn(datasetController);
	}

	@Test
	public void testCallShowsAllDatasets() {
		ListDatasetsCommand cmd = new ListDatasetsCommand();
		cmd.parent = mockApp;
		cmd.call();
		verify(datasetController).allDatasets();
	}

}
