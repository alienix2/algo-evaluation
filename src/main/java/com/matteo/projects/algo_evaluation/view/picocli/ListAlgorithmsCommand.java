package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "list-algorithms")
public class ListAlgorithmsCommand implements Callable<Void> {

	@ParentCommand
	AlgorithmPicocliApp parent;

	@Override
	public Void call() {
		parent.getAlgorithmController().allAlgorithms();
		return null;
	}

}
