package com.matteo.projects.algo_evaluation.repository.mongo;

import java.net.InetSocketAddress;

import static com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository.DATASET_COLLECTION_NAME;
import static com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository.ALGO_EVALUATION_DB_NAME;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class DatasetMongoRepositoryTest {
	
	private static MongoServer mongoServer;
	private static InetSocketAddress serverAddress;
	
	private MongoClient mongoClient;
	private DatasetMongoRepository datasetRepository;
	private MongoCollection<Document> datasetCollection;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		mongoServer = new MongoServer(new MemoryBackend());
		serverAddress = mongoServer.bind();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		mongoServer.shutdown();
	}

	@Before
	public void setup() {
		mongoClient = new MongoClient(new ServerAddress(serverAddress));
		MongoDatabase database = mongoClient.getDatabase(ALGO_EVALUATION_DB_NAME);
		// Clear the database before each test
		database.drop();
		datasetRepository = new DatasetMongoRepository(mongoClient);
		datasetCollection = database.getCollection(DATASET_COLLECTION_NAME);
	}
	
	@Test
	public void testFindAllDatabaseIsEmpty() {
		assert(datasetRepository.findAll().isEmpty());
	}

}
