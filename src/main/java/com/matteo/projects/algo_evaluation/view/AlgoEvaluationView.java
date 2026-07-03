package com.matteo.projects.algo_evaluation.view;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;

public interface AlgoEvaluationView {

	void showAllAlgorithms(List<Algorithm> asList);
	void algorithmAdded(Algorithm algorithm);
	void algorithmDeleted(Algorithm algo);	
	void showAlgorithmError(String string, Algorithm existing);
	
	void showAllDatasets(List<Dataset> asList);
	void datasetAdded(Dataset dataset);
	void datasetDeleted(Dataset dataset);
	void showDatasetError(String string, Dataset existing);
	
	void showAllRuns(List<Run> asList);
	void runAdded(Run run);
	void runDeleted(Run run);
	void showRunError(String string, Run existing);

}
