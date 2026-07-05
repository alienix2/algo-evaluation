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
	public void showAlgorithmError(String string, Algorithm existing) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAllDatasets(List<Dataset> asList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDatasetError(String string, Dataset existing) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAllRuns(List<Run> asList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runAdded(Run run) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showRunError(String string, Run existing) {
		// TODO Auto-generated method stub

	}

}
