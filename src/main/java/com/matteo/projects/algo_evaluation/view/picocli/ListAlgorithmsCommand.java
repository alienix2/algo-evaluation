package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import com.matteo.projects.algo_evaluation.controller.AlgorithmController;

import picocli.CommandLine.Command;

@Command(name = "list-algorithms")
public class ListAlgorithmsCommand implements Callable<Void> {
	
	private final AlgorithmController algorithmController;

	public ListAlgorithmsCommand(AlgorithmController algorithmController) {
		this.algorithmController = algorithmController;
	}

	@Override
	public Void call() {
		algorithmController.allAlgorithms();
		return null;
	}

}
