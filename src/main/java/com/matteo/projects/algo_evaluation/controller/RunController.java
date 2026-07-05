package com.matteo.projects.algo_evaluation.controller;

import java.time.Clock;
import java.util.UUID;

import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithm;
import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithmRegistry;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.model.Run;
import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;
import com.matteo.projects.algo_evaluation.repository.DatasetRepository;
import com.matteo.projects.algo_evaluation.repository.RunRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class RunController {

	private AlgoEvaluationView runView;
	private RunRepository runRepository;
	private AlgorithmRepository algorithmRepository;
	private DatasetRepository datasetRepository;
	private SortingAlgorithmRegistry registry;
	private Clock clock;

	public RunController(AlgoEvaluationView runView, RunRepository runRepository,
			AlgorithmRepository algorithmRepository, DatasetRepository datasetRepository,
			SortingAlgorithmRegistry registry, Clock clock) {
		this.runView = runView;
		this.runRepository = runRepository;
		this.algorithmRepository = algorithmRepository;
		this.datasetRepository = datasetRepository;
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
		if (algorithmRepository.findById(algorithm.getId()) == null) {
			runView.showAlgorithmError("Algorithm not found in DB: " + algorithm.getName(), algorithm);
			return;
		}
		if (datasetRepository.findById(dataset.getId()) == null) {
			runView.showDatasetError("Dataset not found in DB: " + dataset.getName(), dataset);
			return;
		}
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
