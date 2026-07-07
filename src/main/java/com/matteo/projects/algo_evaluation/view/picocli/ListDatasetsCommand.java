package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "list-datasets")
public class ListDatasetsCommand implements Callable<Void> {
	
	@ParentCommand
	AlgorithmPicocliApp parent;

	@Override
	public Void call() {
		parent.getDatasetController().allDatasets();
		return null;
	}

}
