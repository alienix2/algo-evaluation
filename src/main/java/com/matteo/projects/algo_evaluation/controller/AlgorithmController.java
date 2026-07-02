package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.model.Algorithm;
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

	public void newAlgorithm(Algorithm algorithm) {
		Algorithm existing = algorithmRepository.findById(algorithm.getId());
		if (existing != null) {
			algorithmView.showAlgorithmError("Already existing algorithm with id " + algorithm.getId(), existing);
			return;
		}

		algorithmRepository.save(algorithm);
		algorithmView.algorithmAdded(algorithm);
	}

	public void deleteAlgorithm(Algorithm algorithm) {
		Algorithm existing = algorithmRepository.findById(algorithm.getId());
		if (existing == null) {
			algorithmView.showAlgorithmError("No existing algorithm with id " + algorithm.getId(), algorithm);
			return;
		}
		
		algorithmRepository.delete(algorithm);
		algorithmView.algorithmDeleted(algorithm);
	}
}
