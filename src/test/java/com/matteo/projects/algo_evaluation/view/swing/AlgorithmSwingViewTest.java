package com.matteo.projects.algo_evaluation.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

@RunWith(GUITestRunner.class)
public class AlgorithmSwingViewTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private AlgorithmSwingView algorithmSwingView;

	@Override
	protected void onSetUp() {
		GuiActionRunner.execute(() -> {
			algorithmSwingView = new AlgorithmSwingView();
			return algorithmSwingView;
		});
		window = new FrameFixture(robot(), algorithmSwingView);
		window.show();
	}

	@Test
	@GUITest
	public void testControlsInitialStates() {
		window.list("algorithmList");
		window.list("datasetList");
		window.list("runList");
		window.button(JButtonMatcher.withText("Run")).requireDisabled();
		window.label("errorLabel").requireText(" ");
	}

	@Test
	@GUITest
	public void testShowAllAlgorithmsShouldAddAlgorithmDescriptionsToTheList() {
		Algorithm algo1 = new Algorithm("1", "Bubblesort");
		Algorithm algo2 = new Algorithm("2", "Selectionsort");
		GuiActionRunner.execute(() -> algorithmSwingView.showAllAlgorithms(Arrays.asList(algo1, algo2)));
		String[] listContents = window.list("algorithmList").contents();
		assertThat(listContents).containsExactly(algo1.toString(), algo2.toString());
	}
	
	@Test
	@GUITest
	public void testShowAllDatasetsShouldAddDatasetsDescriptionsToTheList() {
		Dataset dataset1 = new Dataset("1", "Dataset1", Arrays.asList(1, 2, 3));
		Dataset dataset2 = new Dataset("2", "Dataset2", Arrays.asList(4, 5, 6));
		GuiActionRunner.execute(() -> algorithmSwingView.showAllDatasets(Arrays.asList(dataset1, dataset2)));
		String[] listContents = window.list("datasetList").contents();
		assertThat(listContents).containsExactly(dataset1.toString(), dataset2.toString());
	}
	
	@Test
	@GUITest
	public void testShowAllRunsShouldAddRunsDescriptionsToTheList() {
		Run run1 = new Run("1", "1", "1", 1);
		Run run2 = new Run("2", "2", "2", 2);
		GuiActionRunner.execute(() -> algorithmSwingView.showAllRuns(Arrays.asList(run1, run2)));
		String[] listContents = window.list("runList").contents();
		assertThat(listContents).containsExactly(run1.toString(), run2.toString());
	}

}
