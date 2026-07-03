package com.matteo.projects.algo_evaluation.repository.mongo;

import static com.matteo.projects.algo_evaluation.repository.mongo.RunMongoRepository.ALGO_EVALUATION_DB_NAME;
import static com.matteo.projects.algo_evaluation.repository.mongo.RunMongoRepository.RUN_COLLECTION_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.matteo.projects.algo_evaluation.model.Run;
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
		runCollection = database.getCollection(RUN_COLLECTION_NAME);
	}
	
	@Test
	public void testFindAllDatabaseIsEmpty() {
		assertThat(runMongoRepository.findAll()).isEmpty();
	}
	
	@Test
	public void testFindAllDatabaseIsNotEmpty() {
		addTestRunToDatabase("1", "1", "1", 1);
		addTestRunToDatabase("2", "2", "2", 2);
		assertThat(runMongoRepository.findAll()).containsExactly(
				new Run("1", "1", "1", 1),
				new Run("2", "2", "2", 2)
		);
	}
	
	@Test
	public void testFindByIdNotFound() {
		assertThat(runMongoRepository.findById("nonexistent")).isNull();
	}
	
	@Test
	public void testFindByIdFound() {
		addTestRunToDatabase("1", "1", "1", 1);
		Run run = runMongoRepository.findById("1");
		assertThat(run).isEqualTo(new Run("1", "1", "1", 1));
	}
	
	@Test
	public void testSaveRun() {
		Run run = new Run("1", "1", "1", 1);
		runMongoRepository.save(run);
		assertThat(runCollection.find(new Document("_id", "1")).first()).isNotNull();
	}
	
	@Test
	public void testDeleteRun() {
		addTestRunToDatabase("1", "1", "1", 1);
		runMongoRepository.delete(new Run("1", "1", "1", 1));
		assertThat(runCollection.find(new Document("_id", "1")).first()).isNull();
	}
	
	private void addTestRunToDatabase(String id, String algorithmId, String datasetId, long executionTime) {
		Document doc = new Document("_id", id)
				.append("algorithmId", algorithmId)
				.append("datasetId", datasetId)
				.append("executionTime", executionTime);
		runCollection.insertOne(doc);
	}
	
}
