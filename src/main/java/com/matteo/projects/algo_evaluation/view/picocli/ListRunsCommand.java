package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import com.matteo.projects.algo_evaluation.controller.RunController;

import picocli.CommandLine.Command;

@Command(name = "list-runs")
public class ListRunsCommand implements Callable<Void> {
	
	private final RunController runController;
	
	public ListRunsCommand(RunController runController) {
		this.runController = runController;
	}

	@Override
	public Void call() {
		runController.allRuns();
		return null;
	}

}
