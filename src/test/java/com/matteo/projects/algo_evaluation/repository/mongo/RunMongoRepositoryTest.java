package com.matteo.projects.algo_evaluation.repository.mongo;

import static com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository.ALGO_EVALUATION_DB_NAME;
import static com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository.DATASET_COLLECTION_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class RunMongoRepositoryTest {
	private static MongoServer mongoServer;
	private static InetSocketAddress serverAddress;
	
	private MongoClient mongoClient;
	private RunMongoRepository runMongoRepository;
	private MongoCollection<Document> runCollection;
	
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
		runMongoRepository = new RunMongoRepository(mongoClient);
		runCollection = database.getCollection(DATASET_COLLECTION_NAME);
	}
	
	@Test
	public void testFindAllDatabaseIsEmpty() {
		assertThat(runMongoRepository.findAll()).isEmpty();
	}
}
