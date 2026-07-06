package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import com.matteo.projects.algo_evaluation.controller.AlgorithmController;
import com.matteo.projects.algo_evaluation.controller.DatasetController;
import com.matteo.projects.algo_evaluation.controller.RunController;
import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "new-run")
public class NewRunCommand implements Callable<Void>{
	
	private final AlgorithmController algorithmController;
	private final DatasetController datasetController;
	private final RunController runController;
	
	@Option(names = "--algorithm-id", required = true)
    private String algorithmId;
	
	@Option(names = "--dataset-id", required = true)
    private String datasetId;

	public NewRunCommand(AlgorithmController algorithmController, DatasetController datasetController,
			RunController runController) {
		this.algorithmController = algorithmController;
		this.datasetController = datasetController;
		this.runController = runController;
	}
	
	@Override
    public Void call() {
        Algorithm algorithm = algorithmController.findById(algorithmId);
        Dataset dataset = datasetController.findById(datasetId);
        runController.newRun(algorithm, dataset);
        return null;
    }

}
