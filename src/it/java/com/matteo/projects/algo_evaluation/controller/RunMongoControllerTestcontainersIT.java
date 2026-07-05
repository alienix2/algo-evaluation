package com.matteo.projects.algo_evaluation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.time.Clock;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;

import static java.util.Arrays.asList;

import com.matteo.projects.algo_evaluation.algorithm.BubbleSort;
import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithmRegistry;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
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
	
	@Mock
	private Clock clock;
	
	@Captor
	private ArgumentCaptor<Run> runCaptor;

	private RunRepository runRepository;
	private RunController runController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		runRepository = new RunMongoRepository(
				new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017))),
				"algo_evaluation");
		for (Run run : runRepository.findAll()) {
			runRepository.delete(run);
		}
		SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
		registry.register("BubbleSort", new BubbleSort());
		runController = new RunController(runView, runRepository, registry, clock);
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

	@Test
	public void testCreateNewRun() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Dataset dataset = new Dataset("2", "dataset2", asList(3, 1, 2));
		runController.newRun(algorithm, dataset);
		verify(runView).runAdded(runCaptor.capture());
		assertThat(runCaptor.getValue().getAlgorithmId()).isEqualTo("1");
		assertThat(runCaptor.getValue().getDatasetId()).isEqualTo("2");
	}

}
