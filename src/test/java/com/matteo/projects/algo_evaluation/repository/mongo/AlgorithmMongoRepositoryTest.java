package com.matteo.projects.algo_evaluation.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import static com.matteo.projects.algo_evaluation.repository.mongo.AlgorithmMongoRepository.ALGORITHM_COLLECTION_NAME;
import static com.matteo.projects.algo_evaluation.repository.mongo.AlgorithmMongoRepository.ALGO_EVALUATION_DB_NAME;

import java.net.InetSocketAddress;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.bson.Document;

import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class AlgorithmMongoRepositoryTest {

	private static MongoServer mongoServer;
	private static InetSocketAddress serverAddress;

	private MongoClient mongoClient;
	private AlgorithmMongoRepository algorithmRepository;
	private MongoCollection<Document> algorithmCollection;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongoServer = new MongoServer(new MemoryBackend());
		serverAddress = mongoServer.bind();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mongoServer.shutdown();
	}
	
	@Before
	public void setup() {
		mongoClient = new MongoClient(new ServerAddress(serverAddress));
		algorithmRepository = new AlgorithmMongoRepository(mongoClient);
		MongoDatabase database = mongoClient.getDatabase(ALGO_EVALUATION_DB_NAME);
		// make sure we always start with a clean database
		database.drop();
		algorithmCollection = database.getCollection(ALGORITHM_COLLECTION_NAME);
	}
	
	@After
	public void tearDown() {
		mongoClient.close();
	}

}
