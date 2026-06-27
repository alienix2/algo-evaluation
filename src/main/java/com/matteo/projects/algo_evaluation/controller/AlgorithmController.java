package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;
import com.matteo.projects.algo_evaluation.view.AlgorithmView;

public class AlgorithmController {

	private AlgorithmView algorithmView;
	private AlgorithmRepository algorithmRepository;

	public AlgorithmController(AlgorithmView algorithmView, AlgorithmRepository algorithmRepository) {
		this.algorithmView = algorithmView;
		this.algorithmRepository = algorithmRepository;
	}

	public void allAlgorithms() {
		algorithmView.showAllAlgorithms(algorithmRepository.findAll());
	}

	public void newAlgorithm(Algorithm algorithm) {
		Algorithm existing = algorithmRepository.findById(algorithm.getId());
		if (existing != null) {
			algorithmView.showError("Already existing algorithm with id " + algorithm.getId(), existing);
			return;
		}

		algorithmRepository.save(algorithm);
		algorithmView.algorithmAdded(algorithm);
	}
}
