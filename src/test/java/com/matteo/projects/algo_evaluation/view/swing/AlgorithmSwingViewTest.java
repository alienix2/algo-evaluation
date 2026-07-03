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

}
