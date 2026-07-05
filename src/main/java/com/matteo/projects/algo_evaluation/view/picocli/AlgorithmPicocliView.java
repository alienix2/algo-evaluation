package com.matteo.projects.algo_evaluation.view.picocli;

import java.io.PrintStream;
import java.util.List;

import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

public class AlgorithmPicocliView implements AlgoEvaluationView {

	private PrintStream printStream;

	public AlgorithmPicocliView(PrintStream printStream) {
		this.printStream = printStream;
	}

	@Override
	public void showAllAlgorithms(List<Algorithm> algorithms) {
		printStream.println("ID - Name");
		algorithms.forEach(algorithm -> printStream.println(algorithm.getId() + " - " + algorithm.getName()));
	}

	@Override
	public void showAllDatasets(List<Dataset> datasets) {
		printStream.println("ID - Name - Integers");
		datasets.forEach(dataset -> printStream
				.println(dataset.getId() + " - " + dataset.getName() + " - " + dataset.getIntegers()));
	}

	@Override
	public void showAllRuns(List<Run> runs) {
		printStream.println("ID - AlgorithmId - DatasetId - Execution Time (ms)");
		runs.forEach(run -> printStream.println(run.getId() + " - " + run.getAlgorithmId() + " - " + run.getDatasetId()
				+ " - " + run.getExecutionTime()));
	}

	@Override
	public void runAdded(Run run) {
		printStream.println("Run added: " + run.getId());
	}

	@Override
	public void showAlgorithmError(String message, Algorithm existing) {
		printStream.println("ERROR: " + message);
	}

	@Override
	public void showDatasetError(String message, Dataset existing) {
		printStream.println("ERROR: " + message);
	}

	@Override
	public void showRunError(String message, Run existing) {
		printStream.println("ERROR: " + message);
	}

}
