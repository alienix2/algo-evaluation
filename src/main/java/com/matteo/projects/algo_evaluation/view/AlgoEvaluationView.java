package com.matteo.projects.algo_evaluation.view;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

public interface AlgoEvaluationView {

	void showAllAlgorithms(List<Algorithm> asList);
	void showAlgorithmError(String string, Algorithm existing);
	
	void showAllDatasets(List<Dataset> asList);
	void showDatasetError(String string, Dataset existing);
	
	void showAllRuns(List<Run> asList);
	void runAdded(Run run);
	void showRunError(String string, Run existing);

}
