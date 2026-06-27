package com.matteo.projects.algo_evaluation.view;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Algorithm;

public interface AlgorithmView {

	void showAllAlgorithms(List<Algorithm> asList);

	void algorithmAdded(Algorithm algorithm);
	
	void showError(String string, Algorithm existing);

}
