package com.matteo.projects.algo_evaluation.view.swing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import javax.swing.JFrame;
import java.util.Arrays;

@RunWith(GUITestRunner.class)
public class AlgorithmSwingAppE2E extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

	private static final String DB_NAME = "test-db";

	private MongoClient mongoClient;
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		mongoClient.getDatabase(DB_NAME).drop();
		addTestAlgorithm("1", "BubbleSort");
		addTestDataset("1", "Dataset1", Arrays.asList(3, 1, 2));
		application(AlgorithmSwingApp.class.getName()).withArgs("--mongo-host=" + mongo.getHost(),
				"--mongo-port=" + mongo.getFirstMappedPort(), "--db-name=" + DB_NAME).start();
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Sorting Algorithm evluation".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}

	@Test
	@GUITest
	public void testOnStartAllDatabaseElementsAreShown() {
		assertThat(window.list("algorithmList").contents()).anySatisfy(e -> assertThat(e).contains("BubbleSort"));
		assertThat(window.list("datasetList").contents()).anySatisfy(e -> assertThat(e).contains("Dataset"));
	}

	@Test
	@GUITest
	public void testRunButtonSuccess() {
		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();
		assertThat(window.list("runList").contents()).anySatisfy(e -> assertThat(e).contains("1", "1"));
	}

	@Test
	@GUITest
	public void testRunButtonShowsAlgorithmErrorWhenAlgorithmDeletedFromDatabase() {
		mongoClient.getDatabase(DB_NAME).getCollection("algorithms").drop();
		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();
		assertThat(window.label("errorLabel").text()).isEqualTo("Algorithm not found in DB: BubbleSort");
	}

	@Test
	@GUITest
	public void testRunButtonShowsDatasetErrorWhenDatasetDeletedFromDatabase() {
		mongoClient.getDatabase(DB_NAME).getCollection("datasets").drop();
		window.list("algorithmList").selectItem(0);
		window.list("datasetList").selectItem(0);
		window.button(JButtonMatcher.withText("Run")).click();
		assertThat(window.label("errorLabel").text()).isEqualTo("Dataset not found in DB: Dataset1");
	}

	private void addTestAlgorithm(String id, String name) {
		mongoClient.getDatabase(DB_NAME).getCollection("algorithms")
				.insertOne(new Document().append("_id", id).append("name", name));
	}

	private void addTestDataset(String id, String name, java.util.List<Integer> integers) {
		mongoClient.getDatabase(DB_NAME).getCollection("datasets")
				.insertOne(new Document().append("_id", id).append("name", name).append("integers", integers));
	}

}