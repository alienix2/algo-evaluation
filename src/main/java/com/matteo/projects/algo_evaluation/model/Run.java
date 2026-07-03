package com.matteo.projects.algo_evaluation.model;

import java.util.Objects;

public class Run {

	private String id;
	private String algorithmId;
	private String datasetId;
	private long executionTime;

	public Run(String id, String algorithmId, String datasetId, long executionTime) {
		super();
		this.id = id;
		this.algorithmId = algorithmId;
		this.datasetId = datasetId;
		this.executionTime = executionTime;
	}

	public String getId() {
		return id;
	}

	public String getAlgorithmId() {
		return algorithmId;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	@Override
	public String toString() {
		return "Run [id=" + id + ", algorithmId=" + algorithmId + ", datasetId=" + datasetId + ", executionTime="
				+ executionTime + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(algorithmId, datasetId, executionTime, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Run other = (Run) obj;
		return Objects.equals(algorithmId, other.algorithmId) && Objects.equals(datasetId, other.datasetId)
				&& executionTime == other.executionTime && Objects.equals(id, other.id);
	}

}
