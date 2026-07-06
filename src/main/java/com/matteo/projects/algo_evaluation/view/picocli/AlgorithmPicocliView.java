package com.matteo.projects.algo_evaluation.view.picocli;

import java.io.PrintWriter;
import java.util.List;

import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

public class AlgorithmPicocliView implements AlgoEvaluationView {

	private static final String ERROR_PREFIX = "ERROR: ";
	private PrintWriter out;

	public AlgorithmPicocliView(PrintWriter out) {
		this.out = out;
	}

	@Override
	public void showAllAlgorithms(List<Algorithm> algorithms) {
		out.println("ID - Name");
		algorithms.forEach(algorithm -> out.println(algorithm.getId() + " - " + algorithm.getName()));
	}

	@Override
	public void showAllDatasets(List<Dataset> datasets) {
		out.println("ID - Name - Integers");
		datasets.forEach(dataset -> out
				.println(dataset.getId() + " - " + dataset.getName() + " - " + dataset.getIntegers()));
	}

	@Override
	public void showAllRuns(List<Run> runs) {
		out.println("ID - AlgorithmId - DatasetId - Execution Time (ms)");
		runs.forEach(run -> out.println(run.getId() + " - " + run.getAlgorithmId() + " - " + run.getDatasetId()
				+ " - " + run.getExecutionTime()));
	}

	@Override
	public void runAdded(Run run) {
		out.println("Run added: " + run.getId());
	}

	@Override
	public void showAlgorithmError(String message, Algorithm existing) {
		out.println(ERROR_PREFIX + message);
	}

	@Override
	public void showDatasetError(String message, Dataset existing) {
		out.println(ERROR_PREFIX + message);
	}

	@Override
	public void showRunError(String message, Run existing) {
		out.println(ERROR_PREFIX + message);
	}

}
