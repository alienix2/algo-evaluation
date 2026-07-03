package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.AlgorithmMongoRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class AlgorithmMongoControllerTestcontainersIT {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

	@Mock
	private AlgoEvaluationView algorithmView;

	private AlgorithmRepository algorithmRepository;
	private AlgorithmController algorithmController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		algorithmRepository = new AlgorithmMongoRepository(
				new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017))));
		for (Algorithm algorithm : algorithmRepository.findAll()) {
			algorithmRepository.delete(algorithm);
		}
		algorithmController = new AlgorithmController(algorithmView, algorithmRepository);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	public void testAllAlgorithms() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		algorithmRepository.save(algorithm);
		algorithmController.allAlgorithms();
		verify(algorithmView).showAllAlgorithms(Arrays.asList(algorithm));
	}

}
