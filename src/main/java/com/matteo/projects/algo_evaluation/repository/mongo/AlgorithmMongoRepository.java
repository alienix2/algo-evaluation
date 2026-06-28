package com.matteo.projects.algo_evaluation.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.repository.AlgorithmRepository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class AlgorithmMongoRepository implements AlgorithmRepository {

	public static final String ALGO_EVALUATION_DB_NAME = "algo_evaluation";
	public static final String ALGORITHM_COLLECTION_NAME = "algorithms";
	private MongoCollection<Document> studentCollection;

	public AlgorithmMongoRepository(MongoClient mongoClient) {
		studentCollection = mongoClient.getDatabase(ALGO_EVALUATION_DB_NAME).getCollection(ALGORITHM_COLLECTION_NAME);
	}

	@Override
	public List<Algorithm> findAll() {
		return StreamSupport.
				stream(studentCollection.find().spliterator(), false)
				.map(doc -> new Algorithm(doc.getString("_id"), doc.getString("name")))
				.collect(Collectors.toList());
	}

	@Override
	public Algorithm findById(String string) {
		return null;
	}

	@Override
	public void save(Algorithm algorithm) {
	}

	@Override
	public void delete(Algorithm algo) {
	}

}
