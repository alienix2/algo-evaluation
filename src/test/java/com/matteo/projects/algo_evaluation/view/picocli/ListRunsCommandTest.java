package com.matteo.projects.algo_evaluation.view.picocli;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.controller.RunController;

public class ListRunsCommandTest {

	@Mock
	AlgorithmPicocliApp mockApp;

	@Mock
	RunController runController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		when(mockApp.getRunController()).thenReturn(runController);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
		when(mockApp.getRunController()).thenReturn(runController);
	}

	@Test
	public void testCallShowsAllRuns() {
		ListRunsCommand cmd = new ListRunsCommand();
		cmd.parent = mockApp;
		cmd.call();
		verify(runController).allRuns();
	}

}
