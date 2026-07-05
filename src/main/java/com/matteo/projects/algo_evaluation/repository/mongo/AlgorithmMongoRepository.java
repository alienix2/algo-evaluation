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

	public static final String ALGORITHM_COLLECTION_NAME = "algorithms";
	private MongoCollection<Document> algorithmCollection;

	public AlgorithmMongoRepository(MongoClient mongoClient, String databaseName) {
		algorithmCollection = mongoClient.getDatabase(databaseName).getCollection(ALGORITHM_COLLECTION_NAME);
	}

	@Override
	public List<Algorithm> findAll() {
		return StreamSupport.
				stream(algorithmCollection.find().spliterator(), false)
				.map(doc -> new Algorithm(doc.getString("_id"), doc.getString("name")))
				.collect(Collectors.toList());
	}

	@Override
	public Algorithm findById(String string) {
		return StreamSupport.stream(algorithmCollection.find(new Document("_id", string)).spliterator(), false)
				.map(doc -> new Algorithm(doc.getString("_id"), doc.getString("name")))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void save(Algorithm algorithm) {
		Document doc = new Document("_id", algorithm.getId())
				.append("name", algorithm.getName());
		algorithmCollection.insertOne(doc);
	}

	@Override
	public void delete(Algorithm algo) {
		algorithmCollection.deleteOne(new Document("_id", algo.getId()));
	}

}
