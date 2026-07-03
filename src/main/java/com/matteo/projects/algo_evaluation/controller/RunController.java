package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.model.Run;
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

	public void newRun(Run run) {
		Run existing = runRepository.findById(run.getId());
		if (existing != null) {
			runView.showRunError("Already existing run with id " + run.getId(), existing);
			return;
		}

		runRepository.save(run);
		runView.runAdded(run);
	}

}
