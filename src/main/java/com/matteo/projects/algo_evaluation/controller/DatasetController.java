package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.repository.DatasetRepository;
import com.matteo.projects.algo_evaluation.view.AlgoEvaluationView;

public class DatasetController {
	
	private AlgoEvaluationView datasetView;
	private DatasetRepository datasetRepository;
	
	public DatasetController(AlgoEvaluationView datasetView, DatasetRepository datasetRepository) {
		this.datasetView = datasetView;
		this.datasetRepository = datasetRepository;
	}
	
	public void allDatasets() {
		datasetView.showAllDatasets(datasetRepository.findAll());
	}

}
