package com.matteo.projects.algo_evaluation.repository;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Dataset;

public interface DatasetRepository {

	List<Dataset> findAll();

	Dataset findById(String id);

	void save(Dataset dataset);

	void delete(Dataset dataset);

}
