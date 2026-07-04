package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;

import java.time.Clock;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithm;
import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithmRegistry;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;
import com.matteo.projects.algo_evaluation.repository.RunRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class RunControllerTest {

	@Mock
	private AlgoEvaluationView runView;

	@Mock
	private RunRepository runRepository;

	@Mock
	private SortingAlgorithmRegistry registry;

	@Mock
	private SortingAlgorithm sortingAlgorithm;
	
	@Mock
	private Clock clock;

	@Captor
	private ArgumentCaptor<Run> runCaptor;

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

	@Test
	public void testNewRunAlgorithmIsFound() {
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		Integer[] unsorted = new Integer[] { 3, 1, 2 };
		Dataset dataset = new Dataset("2", "dataset2", Arrays.asList(unsorted));

		when(sortingAlgorithm.sorted(unsorted)).thenReturn(new Integer[] { 1, 2, 3 });
		when(registry.get("BubbleSort")).thenReturn(sortingAlgorithm);
		when(clock.millis()).thenReturn(1000L, 1500L);
		
		runController.newRun(algorithm, dataset);

		ArgumentCaptor<Run> captor = ArgumentCaptor.forClass(Run.class);
		InOrder inOrder = inOrder(runRepository, runView);
		inOrder.verify(runRepository).save(captor.capture());
		inOrder.verify(runView).runAdded(captor.getValue());
		Run saved = captor.getValue();
		assertThat(saved.getAlgorithmId()).isEqualTo("1");
		assertThat(saved.getDatasetId()).isEqualTo("2");
		assertThat(saved.getExecutionTime()).isEqualTo(500L);
	}

	@Test
	public void testNewRunWhenAlgorithmIsNotFound() {
		Algorithm algorithm = new Algorithm("1", "Unknown");
		Dataset dataset = new Dataset("2", "BubbleSort", Arrays.asList(1, 2, 3));
		when(registry.get("Unknown")).thenReturn(null);

		runController.newRun(algorithm, dataset);

		verify(runView).showAlgorithmError("Algorithm not found: Unknown", algorithm);
		verifyNoMoreInteractions(ignoreStubs(runRepository));
	}
}
