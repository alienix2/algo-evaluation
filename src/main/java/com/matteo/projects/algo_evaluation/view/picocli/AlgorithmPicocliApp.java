package com.matteo.projects.algo_evaluation.view.picocli;

import java.time.Clock;

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
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;
import picocli.CommandLine.Spec;

@Command(name = "algo-evaluation", version = "0.0.1-SNAPSHOT", mixinStandardHelpOptions = true, scope = ScopeType.INHERIT, subcommands = {
		ListAlgorithmsCommand.class, ListDatasetsCommand.class, ListRunsCommand.class, NewRunCommand.class })
public class AlgorithmPicocliApp {

	private RunController runController;
	private AlgorithmController algorithmController;
	private DatasetController datasetController;

	@Spec
	CommandSpec spec;

	@Mixin
	MongoOptions mongoOptions;

	RunController getRunController() {
		return runController;
	}

	DatasetController getDatasetController() {
		return datasetController;
	}

	AlgorithmController getAlgorithmController() {
		return algorithmController;
	}

	private int executionStrategy(ParseResult parseResult) {
		if (isHelpRequested(parseResult)) {
			return new CommandLine.RunLast().execute(parseResult);
		}
		init();
		return new CommandLine.RunLast().execute(parseResult);
	}

	private boolean isHelpRequested(ParseResult parseResult) {
		if (parseResult.commandSpec().commandLine().isUsageHelpRequested()
				|| parseResult.commandSpec().commandLine().isVersionHelpRequested()) {
			return true;
		}
		return parseResult.hasSubcommand() && isHelpRequested(parseResult.subcommand());
	}

	private void init() {
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
	}

	public static void main(String[] args) {
		AlgorithmPicocliApp app = new AlgorithmPicocliApp();
		new CommandLine(app).setExecutionStrategy(app::executionStrategy).execute(args);
	}
}
