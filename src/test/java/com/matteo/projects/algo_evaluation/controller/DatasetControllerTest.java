package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
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

	@Test
	public void testNewDatasetDoesNotAlreadyExist() {
		when(datasetRepository.findById("1")).thenReturn(null);
		Dataset dataset = new Dataset("1", "Dataset1", Arrays.asList(1, 2, 3));
		datasetController.newDataset(dataset);
		InOrder inOrder = inOrder(datasetRepository, datasetView);
		inOrder.verify(datasetRepository).save(dataset);
		inOrder.verify(datasetView).datasetAdded(dataset);
	}

	@Test
	public void testNewDatasetAlreadyExist() {
		Dataset existing = new Dataset("1", "Dataset1", Arrays.asList(1, 2, 3));
		Dataset toAdd = new Dataset("1", "Dataset2", Arrays.asList(4, 5, 6));
		when(datasetRepository.findById("1")).thenReturn(existing);
		datasetController.newDataset(toAdd);
		verify(datasetView).showDatasetError("Already existing dataset with id " + toAdd.getId(), existing);
		verifyNoInteractions(ignoreStubs(datasetRepository));
	}
}
