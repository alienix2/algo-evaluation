package com.matteo.projects.algo_evaluation.view;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;

public interface AlgoEvaluationView {

	void showAllAlgorithms(List<Algorithm> asList);
	void algorithmAdded(Algorithm algorithm);
	void algorithmDeleted(Algorithm algo);	
	void showAlgorithmError(String string, Algorithm existing);
	
	void showAllDatasets(List<Dataset> asList);
	void datasetAdded(Object dataset);
	void datasetDeleted(Object dataset);
	void showDatasetError(String string, Object existing);

}
