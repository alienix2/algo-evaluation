package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
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
	
	@Test
	public void testNewRunDoesNotAlreadyExist() {
		when(runRepository.findById("1")).thenReturn(null);
		Run run = new Run("1", "1", "1", 1);
		runController.newRun(run);
		InOrder inOrder = inOrder(runRepository, runView);
		inOrder.verify(runRepository).save(run);
		inOrder.verify(runView).runAdded(run);
	}
	
	@Test
	public void testNewRunAlreadyExist() {
		Run existing = new Run("1", "1", "1", 1);
		Run toAdd = new Run("1", "2", "2", 2);
		when(runRepository.findById("1")).thenReturn(existing);
		runController.newRun(toAdd);
		verify(runView).showRunError("Already existing run with id " + toAdd.getId(), existing);
		verifyNoMoreInteractions(ignoreStubs(runRepository));
	}

}
