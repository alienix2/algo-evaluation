package com.matteo.projects.algo_evaluation.view.swing;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

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

}
