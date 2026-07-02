package com.matteo.projects.algo_evaluation.controller;

import com.matteo.projects.algo_evaluation.model.Dataset;
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

	public void newDataset(Dataset dataset) {
		Dataset existing = datasetRepository.findById(dataset.getId());
		if (existing != null) {
			datasetView.showDatasetError("Already existing dataset with id " + dataset.getId(), existing);
			return;
		}

		datasetRepository.save(dataset);
		datasetView.datasetAdded(dataset);
	}

}
