package com.matteo.projects.algo_evaluation.repository.mongo;

import java.net.InetSocketAddress;
import java.util.List;

import static com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository.DATASET_COLLECTION_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository.ALGO_EVALUATION_DB_NAME;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.ServerAddress;
import com.matteo.projects.algo_evaluation.model.Dataset;
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
	
	@Test
	public void testFindAllDatasetIsNotEmpty() {
		addTestDatasetToDatabase("1", "Dataset1", List.of(1, 2, 3));
		addTestDatasetToDatabase("2", "Dataset2", List.of(4, 5, 6));
		assertThat(datasetRepository.findAll()).containsExactly(
				new Dataset("1", "Dataset1", List.of(1, 2, 3)),
				new Dataset("2", "Dataset2", List.of(4, 5, 6))
		);
	}
	
	@Test
	public void testFindByIdNotFound() {
		assertThat(datasetRepository.findById("nonexistent")).isNull();
	}
	
	@Test
	public void testFindByIdFound() {
		addTestDatasetToDatabase("1", "Dataset1", List.of(1, 2, 3));
		Dataset dataset = datasetRepository.findById("1");
		assertThat(dataset).isEqualTo(new Dataset("1", "Dataset1", List.of(1, 2, 3)));
	}
	
	@Test
	public void testSaveDataset() {
		Dataset dataset = new Dataset("1", "Dataset1", List.of(1, 2, 3));
		datasetRepository.save(dataset);
		assertThat(datasetCollection.find(new Document("_id", "1")).first()).isNotNull();
	}
	
	@Test
	public void testDeleteDataset() {
		addTestDatasetToDatabase("1", "Dataset1", List.of(1, 2, 3));
		datasetRepository.delete(new Dataset("1", "Dataset1", List.of(1, 2, 3)));
		assertThat(datasetCollection.find(new Document("_id", "1")).first()).isNull();
	}
	
	private void addTestDatasetToDatabase(String id, String name, List<Integer> integers) {
		Document doc = new Document("_id", id)
				.append("name", name)
				.append("integers", integers);
		datasetCollection.insertOne(doc);
	}

}
