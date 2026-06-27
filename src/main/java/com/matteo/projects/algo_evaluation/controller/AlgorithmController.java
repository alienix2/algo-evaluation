package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;
import com.matteo.projects.algo_evaluation.view.AlgorithmView;

public class AlgorithmController {

	private AlgorithmView algorithmView;
    private AlgorithmRepository algorithmRepository;

    public AlgorithmController(AlgorithmView algorithmView, 
                               AlgorithmRepository algorithmRepository) {
        this.algorithmView = algorithmView;
        this.algorithmRepository = algorithmRepository;
    }
	
	public void allAlgorithms() {
	     algorithmView.showAllAlgorithms(algorithmRepository.findAll());
	 }

}
