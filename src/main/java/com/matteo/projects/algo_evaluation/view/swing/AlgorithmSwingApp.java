package com.matteo.projects.algo_evaluation.view.swing;

import java.awt.EventQueue;
import java.time.Clock;
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

public class AlgorithmSwingApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MongoClient mongoClient = new MongoClient("localhost", 27017);

				AlgorithmMongoRepository algorithmRepository = new AlgorithmMongoRepository(mongoClient);
				DatasetMongoRepository datasetRepository = new DatasetMongoRepository(mongoClient);
				RunMongoRepository runRepository = new RunMongoRepository(mongoClient);

				SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
				registry.register("BubbleSort", new BubbleSort());
				registry.register("SelectionSort", new SelectionSort());
				AlgorithmSwingView view = new AlgorithmSwingView();
				RunController runController = new RunController(view, runRepository, registry,
						Clock.systemDefaultZone());
				view.setRunController(runController);

				new AlgorithmController(view, algorithmRepository).allAlgorithms();
				new DatasetController(view, datasetRepository).allDatasets();
				runController.allRuns();
				view.setVisible(true);
			} catch (Exception e) {
				Logger.getLogger(AlgorithmSwingApp.class.getName()).log(Level.SEVERE, "Exception", e);
			}
		});
	}
}
