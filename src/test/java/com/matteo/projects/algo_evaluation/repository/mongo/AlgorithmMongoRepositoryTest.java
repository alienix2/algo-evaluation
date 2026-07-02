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
import com.matteo.projects.algo_evaluation.model.Algorithm;
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
		algorithmRepository = new AlgorithmMongoRepository(mongoClient);
		algorithmCollection = database.getCollection(ALGORITHM_COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	@Test
	public void testFindAllDatabaseIsEmpty() {
		assertThat(algorithmRepository.findAll()).isEmpty();
	}

	@Test
	public void testFindAllDatabaseIsNotEmpty() {
		addTestAlgorithmToDatabase("1", "test1");
		addTestAlgorithmToDatabase("2", "test2");
		assertThat(algorithmRepository.findAll()).containsExactly(new Algorithm("1", "test1"),
				new Algorithm("2", "test2"));
	}

	@Test
	public void testFindByIdNotFound() {
		assertThat(algorithmRepository.findById("nonexistent")).isNull();
	}

	@Test
	public void testFindByIdFound() {
		addTestAlgorithmToDatabase("1", "test1");
		addTestAlgorithmToDatabase("2", "test2");
		assertThat(algorithmRepository.findById("2")).isEqualTo(new Algorithm("2" + "", "test2"));
	}

	@Test
	public void testSaveAlgorithm() {
		Algorithm algorithm = new Algorithm("1", "test1");
		algorithmRepository.save(algorithm);
		assertThat(algorithmCollection.find(new Document("_id", "1")).first()).isNotNull();
	}

	@Test
	public void testDeleteAlgorithm() {
		addTestAlgorithmToDatabase("1", "test1");
		algorithmRepository.delete(new Algorithm("1", "test1"));
		assertThat(algorithmCollection.find(new Document("_id", "1")).first()).isNull();
	}

	private void addTestAlgorithmToDatabase(String id, String name) {
		Document doc = new Document("_id", id).append("name", name);
		algorithmCollection.insertOne(doc);
	}

}
