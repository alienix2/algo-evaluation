package com.matteo.projects.algo_evaluation.repository;

import java.util.List;

import com.matteo.projects.algo_evaluation.model.Algorithm;

public interface AlgorithmRepository {

	List<Algorithm> findAll();

	Algorithm findById(String string);

	void save(Algorithm algorithm);

}
