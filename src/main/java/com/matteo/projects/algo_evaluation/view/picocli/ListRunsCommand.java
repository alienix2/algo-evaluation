package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "list-runs")
public class ListRunsCommand implements Callable<Void> {
	
	@ParentCommand
	AlgorithmPicocliApp parent;

	@Override
	public Void call() {
		parent.getRunController().allRuns();
		return null;
	}

}
