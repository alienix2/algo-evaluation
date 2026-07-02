package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;
import com.mongodb.ServerAddress;

import static java.util.Arrays.asList;

import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.repository.DatasetRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;
import com.mongodb.MongoClient;

public class DatasetMongoControllerTestcontainersIT {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

	@Mock
	private AlgoEvaluationView datasetView;

	private DatasetRepository datasetRepository;
	private DatasetController datasetController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		datasetRepository = new DatasetMongoRepository(
				new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017))));
		for (Dataset dataset : datasetRepository.findAll()) {
			datasetRepository.delete(dataset);
		}
		datasetController = new DatasetController(datasetView, datasetRepository);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllDatasets() {
		Dataset dataset = new Dataset("1", "Dataset1", List.of(1, 2, 3));
		datasetRepository.save(dataset);
		datasetController.allDatasets();
		verify(datasetView).showAllDatasets(asList(dataset));
	}
	
	@Test
	public void testNewDataset() {
		Dataset dataset = new Dataset("1", "Dataset1", List.of(1, 2, 3));
		datasetController.newDataset(dataset);
		verify(datasetView).datasetAdded(dataset);
	}
	
	@Test
	public void testDeleteDataset() {
		Dataset dataset = new Dataset("1", "Dataset1", List.of(1, 2, 3));
		datasetRepository.save(dataset);
		datasetController.deleteDataset(dataset);
		verify(datasetView).datasetDeleted(dataset);
	}

}
