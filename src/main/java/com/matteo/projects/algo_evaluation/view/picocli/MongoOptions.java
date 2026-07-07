package com.matteo.projects.algo_evaluation.view.picocli;

import picocli.CommandLine.Option;

public class MongoOptions {
	@Option(names = "--mongo-host", defaultValue = "localhost")
	String mongoHost;

	@Option(names = "--mongo-port", defaultValue = "27017")
	int mongoPort;

	@Option(names = "--db-name", defaultValue = "algo_evaluation")
	String dbName;
}