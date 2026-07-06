package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

@Command(name = "algo-evaluation", mixinStandardHelpOptions = true, subcommands = { ListAlgorithmsCommand.class,
		ListDatasetsCommand.class, ListRunsCommand.class, NewRunCommand.class })
public class AlgorithmPicocliApp implements Callable<Void> {

	@Mixin
	MongoOptions mongoOptions;

	@Override
	public Void call() {
		return null;
	}

	public static void main(String[] args) {
		new CommandLine(new AlgorithmPicocliApp()).execute(args);
	}
}
