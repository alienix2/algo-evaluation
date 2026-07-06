package com.matteo.projects.algo_evaluation.view.picocli;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.matteo.projects.algo_evaluation.controller.AlgorithmController;

public class ListAlgorithmsCommandTest {

    @Mock
    private AlgorithmController algorithmController;
    
    @Mock
    AlgorithmPicocliApp mockApp;

    private AutoCloseable closeable;

    @Before
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        when(mockApp.getAlgorithmController()).thenReturn(algorithmController);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testCallShowsAllAlgorithms() {
    	ListAlgorithmsCommand cmd = new ListAlgorithmsCommand();
        cmd.parent = mockApp;
        cmd.call();
        verify(algorithmController).allAlgorithms();
    }
}
