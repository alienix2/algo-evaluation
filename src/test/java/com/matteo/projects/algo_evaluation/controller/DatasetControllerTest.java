package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.repository.DatasetRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class DatasetControllerTest {

	@Mock
	private AlgoEvaluationView datasetView;

	@Mock
	private DatasetRepository datasetRepository;

	@InjectMocks
	private DatasetController datasetController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllDatasets() {
		Dataset dataset = new Dataset("1", "Dataset1", Arrays.asList(1, 2, 3));
		when(datasetRepository.findAll()).thenReturn(Arrays.asList(dataset));
		datasetController.allDatasets();
		verify(datasetView).showAllDatasets(Arrays.asList(dataset));
	}

}
