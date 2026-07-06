package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class AlgorithmController {

	private AlgoEvaluationView algorithmView;
	private AlgorithmRepository algorithmRepository;

	public AlgorithmController(AlgoEvaluationView algorithmView, AlgorithmRepository algorithmRepository) {
		this.algorithmView = algorithmView;
		this.algorithmRepository = algorithmRepository;
	}

	public void allAlgorithms() {
		algorithmView.showAllAlgorithms(algorithmRepository.findAll());
	}

	public Object findById(String string) {
		return algorithmRepository.findById(string);
	}

}
