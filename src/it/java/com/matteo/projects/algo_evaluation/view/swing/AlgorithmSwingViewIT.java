package com.matteo.projects.algo_evaluation.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;
import java.time.Clock;
import java.util.Arrays;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.matteo.projects.algo_evaluation.algorithm.BubbleSort;
import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithmRegistry;
import com.matteo.projects.algo_evaluation.controller.RunController;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.repository.mongo.AlgorithmMongoRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.RunMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@RunWith(GUITestRunner.class)
public class AlgorithmSwingViewIT extends AssertJSwingJUnitTestCase {

	private static final String DB_NAME = "test-db";

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	private MongoClient mongoClient;
	private AlgorithmMongoRepository algorithmRepository;
	private DatasetMongoRepository datasetRepository;
	private RunMongoRepository runRepository;
	private AlgorithmSwingView view;
	private FrameFixture window;

	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		serverAddress = server.bind();
	}

	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}

	@Override
	protected void onSetUp() {
		mongoClient = new MongoClient(new ServerAddress(serverAddress));

		algorithmRepository = new AlgorithmMongoRepository(mongoClient, DB_NAME);
		datasetRepository = new DatasetMongoRepository(mongoClient, DB_NAME);
		runRepository = new RunMongoRepository(mongoClient, DB_NAME);

		mongoClient.getDatabase(DB_NAME).drop();

		SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
		registry.register("BubbleSort", new BubbleSort());

		GuiActionRunner.execute(() -> {
			view = new AlgorithmSwingView();
			RunController runController = new RunController(view, runRepository, algorithmRepository, datasetRepository,
					registry, Clock.systemDefaultZone());
			view.setRunController(runController);
			return view;
		});

		window = new FrameFixture(robot(), view);
		window.show();
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}

	@Test
	@GUITest
	public void testRunButtonSuccess() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Dataset dataset = new Dataset("2", "Dataset", Arrays.asList(3, 1, 2));
		algorithmRepository.save(algorithm);
		datasetRepository.save(dataset);

		GuiActionRunner.execute(() -> {
			view.showAllAlgorithms(Arrays.asList(algorithm));
			view.showAllDatasets(Arrays.asList(dataset));
		});

		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();

		assertThat(window.list("runList").contents()).isNotEmpty();
		window.label("errorLabel").requireText(" ");
	}

	@Test
	@GUITest
	public void testRunButtonShowsAlgorithmErrorWhenNotInDB() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Dataset dataset = new Dataset("2", "Dataset", Arrays.asList(3, 1, 2));
		datasetRepository.save(dataset);

		GuiActionRunner.execute(() -> {
			view.showAllAlgorithms(Arrays.asList(algorithm));
			view.showAllDatasets(Arrays.asList(dataset));
		});

		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();

		window.label("errorLabel").requireText("Algorithm not found in DB: BubbleSort");
	}

	@Test
	@GUITest
	public void testRunButtonShowsDatasetErrorWhenNotInDB() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Dataset dataset = new Dataset("2", "Dataset", Arrays.asList(3, 1, 2));
		algorithmRepository.save(algorithm);

		GuiActionRunner.execute(() -> {
			view.showAllAlgorithms(Arrays.asList(algorithm));
			view.showAllDatasets(Arrays.asList(dataset));
		});

		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();

		window.label("errorLabel").requireText("Dataset not found in DB: Dataset");
	}

	@Test
	@GUITest
	public void testRunButtonShowsAlgorithmErrorWhenNotInRegistry() {
		Algorithm algorithm = new Algorithm("1", "FakeSort");
		Dataset dataset = new Dataset("2", "Dataset", Arrays.asList(3, 1, 2));
		algorithmRepository.save(algorithm);
		datasetRepository.save(dataset);

		GuiActionRunner.execute(() -> {
			view.showAllAlgorithms(Arrays.asList(algorithm));
			view.showAllDatasets(Arrays.asList(dataset));
		});

		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();

		window.label("errorLabel").requireText("Algorithm not found: FakeSort");
	}

}
