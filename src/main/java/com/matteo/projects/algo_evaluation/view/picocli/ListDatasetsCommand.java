package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import com.matteo.projects.algo_evaluation.controller.DatasetController;

import picocli.CommandLine.Command;

@Command(name = "list-datasets")
public class ListDatasetsCommand implements Callable<Void> {
	
	private final DatasetController datasetController;

	public ListDatasetsCommand(DatasetController datasetController) {
		this.datasetController = datasetController;
	}

	@Override
	public Void call() {
		datasetController.allDatasets();
		return null;
	}

}
