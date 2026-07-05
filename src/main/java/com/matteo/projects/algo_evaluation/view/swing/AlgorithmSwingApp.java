package com.matteo.projects.algo_evaluation.view.swing;

import java.awt.EventQueue;
import java.time.Clock;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.matteo.projects.algo_evaluation.algorithm.BubbleSort;
import com.matteo.projects.algo_evaluation.algorithm.SelectionSort;
import com.matteo.projects.algo_evaluation.algorithm.SortingAlgorithmRegistry;
import com.matteo.projects.algo_evaluation.controller.AlgorithmController;
import com.matteo.projects.algo_evaluation.controller.DatasetController;
import com.matteo.projects.algo_evaluation.controller.RunController;
import com.matteo.projects.algo_evaluation.repository.mongo.AlgorithmMongoRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.DatasetMongoRepository;
import com.matteo.projects.algo_evaluation.repository.mongo.RunMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class AlgorithmSwingApp implements Callable<Void> {

	@Option(names = { "--mongo-host" }, description = "MongoDB host address")
	private String mongoHost = "localhost";

	@Option(names = { "--mongo-port" }, description = "MongoDB host port")
	private int mongoPort = 27017;

	@Option(names = { "--db-name" }, description = "Database name")
	private String databaseName = "algo_evaluation";

	public static void main(String[] args) {
		new CommandLine(new AlgorithmSwingApp()).execute(args);
	}

	@Override
	public Void call() throws Exception {
		EventQueue.invokeLater(() -> {
			try {
				MongoClient mongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort));

				AlgorithmMongoRepository algorithmRepository = new AlgorithmMongoRepository(mongoClient, databaseName);
				DatasetMongoRepository datasetRepository = new DatasetMongoRepository(mongoClient, databaseName);
				RunMongoRepository runRepository = new RunMongoRepository(mongoClient, databaseName);

				SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
				registry.register("BubbleSort", new BubbleSort());
				registry.register("SelectionSort", new SelectionSort());

				AlgorithmSwingView view = new AlgorithmSwingView();

				RunController runController = new RunController(view, runRepository, algorithmRepository,
						datasetRepository, registry, Clock.systemDefaultZone());
				view.setRunController(runController);

				new AlgorithmController(view, algorithmRepository).allAlgorithms();
				new DatasetController(view, datasetRepository).allDatasets();
				runController.allRuns();

				view.setVisible(true);
			} catch (Exception e) {
				Logger.getLogger(AlgorithmSwingApp.class.getName()).log(Level.SEVERE, "Exception", e);
			}
		});
		return null;
	}
}