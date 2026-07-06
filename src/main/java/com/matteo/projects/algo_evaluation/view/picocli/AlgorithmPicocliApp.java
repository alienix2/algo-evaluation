package com.matteo.projects.algo_evaluation.view.picocli;

import java.time.Clock;
import java.util.concurrent.Callable;

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

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(name = "algo-evaluation", mixinStandardHelpOptions = true, subcommands = { ListAlgorithmsCommand.class,
		ListDatasetsCommand.class, ListRunsCommand.class, NewRunCommand.class })
public class AlgorithmPicocliApp implements Callable<Void> {

	private RunController runController;
	private AlgorithmController algorithmController;
	private DatasetController datasetController;
	
	@Spec
	CommandSpec spec;

	RunController getRunController() {
		return runController;
	}

	DatasetController getDatasetController() {
		return datasetController;
	}

	AlgorithmController getAlgorithmController() {
		return algorithmController;
	}

	@Mixin
	MongoOptions mongoOptions;

	@Override
	public Void call() {
		AlgorithmPicocliView algorithmPicocliView = new AlgorithmPicocliView(spec.commandLine().getOut());

		SortingAlgorithmRegistry registry = new SortingAlgorithmRegistry();
		registry.register("BubbleSort", new BubbleSort());
		registry.register("SelectionSort", new SelectionSort());

		MongoClient mongoClient = new MongoClient(mongoOptions.mongoHost, mongoOptions.mongoPort);
		AlgorithmMongoRepository algorithmRepository = new AlgorithmMongoRepository(mongoClient, mongoOptions.dbName);
		DatasetMongoRepository datasetRepository = new DatasetMongoRepository(mongoClient, mongoOptions.dbName);
		RunMongoRepository runRepository = new RunMongoRepository(mongoClient, mongoOptions.dbName);

		runController = new RunController(algorithmPicocliView, runRepository, algorithmRepository, datasetRepository,
				registry, Clock.systemDefaultZone());
		algorithmController = new AlgorithmController(algorithmPicocliView, algorithmRepository);
		datasetController = new DatasetController(algorithmPicocliView, datasetRepository);

		return null;
	}

	public static void main(String[] args) {
		new CommandLine(new AlgorithmPicocliApp()).setExecutionStrategy(new CommandLine.RunAll()).execute(args);
	}
}
