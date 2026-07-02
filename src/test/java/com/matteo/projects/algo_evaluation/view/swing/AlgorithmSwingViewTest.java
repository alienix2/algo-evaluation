package com.matteo.projects.algo_evaluation.view.swing;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
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

}
