package com.matteo.projects.algo_evaluation.view.picocli;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Stream;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import picocli.CommandLine;

public class AlgorithmPicocliAppE2E {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

	private static final String DB_NAME = "test-db";

	private MongoClient mongoClient;

	private StringWriter outputStream;
	private PrintWriter printWriter;

	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		mongoClient.getDatabase(DB_NAME).drop();
		outputStream = new StringWriter();
		printWriter = new PrintWriter(outputStream);
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	@Test
	public void testListAlgorithmsShowsAllAlgorithms() {
		addTestAlgorithm("1", "BubbleSort");
		addTestAlgorithm("2", "SelectionSort");
		runCliWithDBArgs("list-algorithms");
		assertThat(outputStream.toString()).contains("BubbleSort").contains("SelectionSort");
	}

	@Test
	public void testListDatasetsShowsAllDatasets() {
		addTestDataset("1", "Dataset1", Arrays.asList(3, 1, 2));
		addTestDataset("2", "Dataset2", Arrays.asList(5, 4, 6));
		runCliWithDBArgs("list-datasets");
		assertThat(outputStream.toString()).contains("Dataset1").contains("Dataset2");
	}

	@Test
	public void testListRunsShowsAllRuns() {
		addTestRun("1", "1", "1", 100);
		addTestRun("2", "2", "2", 200);
		runCliWithDBArgs("list-runs");
		assertThat(outputStream.toString()).contains("1 - 1 - 1 - 100").contains("2 - 2 - 2 - 200");
	}

	@Test
	public void testRunAlgorithmOnDataset() {
		addTestAlgorithm("1", "BubbleSort");
		addTestDataset("2", "Dataset2", Arrays.asList(3, 1, 2));
		int exitCode = runCliWithDBArgs("new-run", "--algorithm-id=1", "--dataset-id=2");
		assertThat(exitCode).isZero();
		assertThat(outputStream.toString()).contains("Run added");
		assertThat(outputStream.toString()).contains("ID - AlgorithmId - DatasetId - Execution Time (ms)");
		assertThat(outputStream.toString()).contains("1 - 2");
	}

	@Test
	public void testNewRunShowsErrorWhenDatasetNotFound() {
		addTestAlgorithm("1", "BubbleSort");
		runCliWithDBArgs("new-run", "--algorithm-id=1", "--dataset-id=1");
		assertThat(outputStream.toString()).contains("ERROR: Dataset not found in DB: 1");
	}

	@Test
	public void testNewRunShowsErrorWhenAlgorithmNotFound() {
		addTestDataset("1", "Dataset1", Arrays.asList(3, 1, 2));
		runCliWithDBArgs("new-run", "--algorithm-id=1", "--dataset-id=1");
		assertThat(outputStream.toString()).contains("ERROR: Algorithm not found in DB: 1");
	}

	@Test
	public void testNewRunShowsErrorWhenAlgorithmNotInRegistry() {
		addTestAlgorithm("1", "FakeSort");
		addTestDataset("2", "Dataset1", Arrays.asList(3, 1, 2));
		runCliWithDBArgs("new-run", "--algorithm-id=1", "--dataset-id=2");
		assertThat(outputStream.toString()).contains("ERROR: Algorithm not found: FakeSort");
	}

	private int runCliWithDBArgs(String... subCommand) {
		AlgorithmPicocliApp app = new AlgorithmPicocliApp();
		CommandLine cmd = app.createCommandLine();

		cmd.setOut(printWriter);
		cmd.setErr(printWriter);

		String[] combinedArgs = Stream.concat(Stream.of("--mongo-host=" + mongo.getHost(),
				"--mongo-port=" + mongo.getFirstMappedPort(), "--db-name=" + DB_NAME), Arrays.stream(subCommand))
				.toArray(String[]::new);

		return cmd.execute(combinedArgs);
	}

	private void addTestAlgorithm(String id, String name) {
		mongoClient.getDatabase(DB_NAME).getCollection("algorithms")
				.insertOne(new Document().append("_id", id).append("name", name));
	}

	private void addTestDataset(String id, String name, java.util.List<Integer> integers) {
		mongoClient.getDatabase(DB_NAME).getCollection("datasets")
				.insertOne(new Document().append("_id", id).append("name", name).append("integers", integers));
	}

	private void addTestRun(String id, String algorithmId, String datasetId, long executionTime) {
		mongoClient.getDatabase(DB_NAME).getCollection("runs")
				.insertOne(new Document().append("_id", id).append("algorithmId", algorithmId)
						.append("datasetId", datasetId).append("executionTime", executionTime));
	}

}
