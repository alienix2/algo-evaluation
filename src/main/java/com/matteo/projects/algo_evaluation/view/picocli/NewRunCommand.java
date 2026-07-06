package com.matteo.projects.algo_evaluation.view.picocli;

import java.util.concurrent.Callable;

import com.matteo.projects.algo_evaluation.model.Algorithm;
import com.matteo.projects.algo_evaluation.model.Dataset;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(name = "new-run")
public class NewRunCommand implements Callable<Void>{
	
	@ParentCommand
	AlgorithmPicocliApp parent;
	
	@Option(names = "--algorithm-id", required = true)
    private String algorithmId;
	
	@Option(names = "--dataset-id", required = true)
    private String datasetId;
	
	@Override
    public Void call() {
        Algorithm algorithm = parent.getAlgorithmController().findById(algorithmId);
        Dataset dataset = parent.getDatasetController().findById(datasetId);
        parent.getRunController().newRun(algorithm, dataset);
        parent.getRunController().allRuns();
        return null;
    }

}
