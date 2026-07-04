package com.matteo.projects.algo_evaluation.controller;

import java.time.Clock;
import java.util.UUID;

import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithm;
import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithmRegistry;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;
import com.matteo.projects.algo_evaluation.repository.RunRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class RunController {

	private AlgoEvaluationView runView;
	private RunRepository runRepository;
	private SortingAlgorithmRegistry registry;
	private Clock clock;

	public RunController(AlgoEvaluationView runView, RunRepository runRepository, SortingAlgorithmRegistry registry,
			Clock clock) {
		this.runView = runView;
		this.runRepository = runRepository;
		this.registry = registry;
		this.clock = clock;
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

	public void newRun(Algorithm algorithm, Dataset dataset) {
		SortingAlgorithm sortingAlgorithm = registry.get(algorithm.getName());

		if (sortingAlgorithm == null) {
			runView.showAlgorithmError("Algorithm not found: " + algorithm.getName(), algorithm);
			return;
		}

		long start = clock.millis();
		sortingAlgorithm.sorted(dataset.getIntegers().toArray(new Integer[0]));
		long executionTime = clock.millis() - start;

		Run run = new Run(UUID.randomUUID().toString(), algorithm.getId(), dataset.getId(), executionTime);
		runRepository.save(run);
		runView.runAdded(run);
	}

}
