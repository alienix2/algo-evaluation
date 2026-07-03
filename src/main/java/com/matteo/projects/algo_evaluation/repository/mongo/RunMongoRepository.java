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
	public static final String RUN_COLLECTION_NAME = "runs";
	private MongoCollection<Document> runCollection;

	public RunMongoRepository(MongoClient mongoClient) {
		runCollection = mongoClient.getDatabase(ALGO_EVALUATION_DB_NAME).getCollection(RUN_COLLECTION_NAME);
	}

	@Override
	public List<Run> findAll() {
		return StreamSupport
				.stream(runCollection.find().spliterator(), false)
				.map(doc -> new Run(doc.getString("_id"), doc.getString("algorithmId"), doc.getString("datasetId"), doc.getLong("executionTime")))
				.collect(Collectors.toList());
	}

	@Override
	public Run findById(String id) {
		return StreamSupport
				.stream(runCollection.find(new Document("_id", id)).spliterator(), false)
				.map(doc -> new Run(doc.getString("_id"), doc.getString("algorithmId"), doc.getString("datasetId"), doc.getLong("executionTime")))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void save(Run run) {
		Document doc = new Document("_id", run.getId())
				.append("algorithmId", run.getAlgorithmId())
				.append("datasetId", run.getDatasetId())
				.append("executionTime", run.getExecutionTime());
		runCollection.insertOne(doc);
	}

	@Override
	public void delete(Run run) {
		runCollection.deleteOne(new Document("_id", run.getId()));
	}

}
