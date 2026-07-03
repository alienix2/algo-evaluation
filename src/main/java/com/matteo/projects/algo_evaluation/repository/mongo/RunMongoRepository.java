package com.matteo.projects.algo_evaluation.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.matteo.projects.algo_evaluation.model.Run;
import com.matteo.projects.algo_evaluation.repository.RunRepository;

public class RunMongoRepository implements RunRepository {
	
	public static final String ALGO_EVALUATION_DB_NAME = "algo_evaluation";
	public static final String ALGORITHM_COLLECTION_NAME = "runs";
	private MongoCollection<Document> algorithmCollection;

	public RunMongoRepository(MongoClient mongoClient) {
		algorithmCollection = mongoClient.getDatabase(ALGO_EVALUATION_DB_NAME).getCollection(ALGORITHM_COLLECTION_NAME);
	}

	@Override
	public List<Run> findAll() {
		return StreamSupport
				.stream(algorithmCollection.find().spliterator(), false)
				.map(doc -> new Run(doc.getString("_id"), doc.getString("algorithmId"), doc.getString("datasetId"), doc.getLong("executionTime")))
				.collect(Collectors.toList());
	}

	@Override
	public Run findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Run run) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Run run) {
		// TODO Auto-generated method stub
		
	}

}
