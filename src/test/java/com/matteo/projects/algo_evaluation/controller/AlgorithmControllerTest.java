package com.matteo.projects.algo_evaluation.controller;

import static org.mockito.Mockito.*;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
}
