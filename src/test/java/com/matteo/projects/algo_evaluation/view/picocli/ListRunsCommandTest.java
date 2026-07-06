package com.matteo.projects.algo_evaluation.view.picocli;

import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.controller.RunController;

public class ListRunsCommandTest {

	@Mock
	private RunController runController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testCallShowsAllRuns() {
		ListRunsCommand cmd = new ListRunsCommand(runController);
		cmd.call();
		verify(runController).allRuns();
	}

}
