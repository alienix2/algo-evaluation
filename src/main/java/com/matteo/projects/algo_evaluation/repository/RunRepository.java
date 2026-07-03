package com.matteo.projects.algo_evaluation.repository;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Run;

public interface RunRepository {
	
	List<Run> findAll();

	Run findById(String id);

	void save(Run run);

	void delete(Run run);

}
