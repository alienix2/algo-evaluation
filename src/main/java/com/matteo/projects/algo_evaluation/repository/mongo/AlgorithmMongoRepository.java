package com.matteo.projects.algo_evaluation.repository.mongo;

import com.mongodb.MongoClient;

public class AlgorithmMongoRepository {
	
	public static final String ALGO_EVALUATION_DB_NAME = "algo_evaluation";
	public static final String ALGORITHM_COLLECTION_NAME = "algorithms";

	public AlgorithmMongoRepository(MongoClient mongoClient) {
	}

}
