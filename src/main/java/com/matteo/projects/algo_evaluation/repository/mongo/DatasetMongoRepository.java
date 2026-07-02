package com.matteo.projects.algo_evaluation.repository.mongo;

import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.matteo.projects.algo_evaluation.model.Dataset;
import com.matteo.projects.algo_evaluation.repository.DatasetRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class DatasetMongoRepository implements DatasetRepository {

	public static final String ALGO_EVALUATION_DB_NAME = "dataset_evaluation";
	public static final String DATASET_COLLECTION_NAME = "datasets";
	private MongoCollection<Document> datasetCollection;

	public DatasetMongoRepository(MongoClient mongoClient) {
		datasetCollection = mongoClient.getDatabase(ALGO_EVALUATION_DB_NAME).getCollection(DATASET_COLLECTION_NAME);
	}

	@Override
	public List<Dataset> findAll() {
		return StreamSupport
				.stream(datasetCollection.find().spliterator(), false)
				.map(doc -> new Dataset(doc.getString("_id"), doc.getString("name"), doc.getList("integers", Integer.class)))
				.collect(Collectors.toList());
	}

	@Override
	public Dataset findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Dataset dataset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Dataset dataset) {
		// TODO Auto-generated method stub

	}

}
