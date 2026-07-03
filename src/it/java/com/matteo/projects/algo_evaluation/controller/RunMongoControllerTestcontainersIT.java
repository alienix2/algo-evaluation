package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;

import static java.util.Arrays.asList;

import com.matteo.projects.algo_evaluation.model.Run;
import com.matteo.projects.algo_evaluation.repository.RunRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.RunMongoRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class RunMongoControllerTestcontainersIT {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

	@Mock
	private AlgoEvaluationView runView;

	private RunRepository runRepository;
	private RunController runController;

	private AutoCloseable closeable;
	
	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		runRepository = new RunMongoRepository(
				new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017))));
		for (Run run : runRepository.findAll()) {
			runRepository.delete(run);
		}
		runController = new RunController(runView, runRepository);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	public void testAllRuns() {
		Run run = new Run("1", "1", "1", 1);
		runRepository.save(run);
		runController.allRuns();
		verify(runView).showAllRuns(asList(run));
	}
	
	@Test
	public void testNewRun() {
		Run run = new Run("1", "1", "1", 1);
		runController.newRun(run);
		verify(runView).runAdded(run);
	}

}
