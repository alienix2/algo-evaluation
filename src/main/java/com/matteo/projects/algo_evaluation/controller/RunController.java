package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.repository.RunRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class RunController {
	
	private AlgoEvaluationView runView;
	private RunRepository runRepository;
	
	public RunController(AlgoEvaluationView runView, RunRepository runRepository) {
		this.runView = runView;
		this.runRepository = runRepository;
	}

	public void allRuns() {
		runView.showAllRuns(runRepository.findAll());
	}

}
