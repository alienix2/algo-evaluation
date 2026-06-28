package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.*;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;
import com.matteo.projects.algo_evaluation.view.AlgorithmView;

public class AlgorithmControllerTest {

	@Mock
	private AlgorithmView algorithmView;

	@Mock
	private AlgorithmRepository algorithmRepository;

	@InjectMocks
	private AlgorithmController algorithmController;

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
	public void testAllAlgorithms() {
		when(algorithmRepository.findAll()).thenReturn(Arrays.asList(new Algorithm("1", "BubbleSort")));
		algorithmController.allAlgorithms();
		verify(algorithmView).showAllAlgorithms(Arrays.asList(new Algorithm("1", "BubbleSort")));
	}

	@Test
	public void testNewAlgorithmDoesNotAlreadyExist() {
		when(algorithmRepository.findById("1")).thenReturn(null);
		Algorithm algorithm = new Algorithm("1", "BubbleSort");
		algorithmController.newAlgorithm(algorithm);
		InOrder inOrder = inOrder(algorithmRepository, algorithmView);
		inOrder.verify(algorithmRepository).save(algorithm);
		inOrder.verify(algorithmView).algorithmAdded(algorithm);
	}

	@Test
	public void testNewAlgorithmAlreadyExist() {
		Algorithm existing = new Algorithm("1", "BubbleSort");
		Algorithm toAdd = new Algorithm("1", "AnotherSort");
		when(algorithmRepository.findById("1")).thenReturn(existing);
		algorithmController.newAlgorithm(toAdd);
		verify(algorithmView).showError("Already existing algorithm with id 1", existing);
		verifyNoMoreInteractions(ignoreStubs(algorithmRepository));
	}
	
	@Test
	public void testDeleteAlgorithmDoesNotAlreadyExist() {
		Algorithm algo = new Algorithm("1", "BubbleSort");
		when(algorithmRepository.findById("1")).thenReturn(null);
		algorithmController.deleteAlgorithm(algo);
		verify(algorithmView).showError("No existing algorithm with id 1", algo);
		verifyNoMoreInteractions(ignoreStubs(algorithmRepository));
	}
	
	@Test
	public void testDeleteAlgorithmAlreadyExist() {
		Algorithm algo = new Algorithm("1", "BubbleSort");
		when(algorithmRepository.findById("1")).thenReturn(algo);
		algorithmController.deleteAlgorithm(algo);
		InOrder inOrder = inOrder(algorithmRepository, algorithmView);
		inOrder.verify(algorithmRepository).delete(algo);
		inOrder.verify(algorithmView).algorithmDeleted(algo);
	}
}
