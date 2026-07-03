package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.model.Run;
import com.matteo.projects.algo_evaluation.repository.RunRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class RunControllerTest {

	@Mock
	private AlgoEvaluationView runView;
	
	@Mock
	private RunRepository runRepository;
	
	@InjectMocks
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
	public void testAllRuns() {
		Run run = new Run("1", "1", "1", 1);
		when(runRepository.findAll()).thenReturn(Arrays.asList(run));
		runController.allRuns();
		verify(runView).showAllRuns(Arrays.asList(run));
	}

}
