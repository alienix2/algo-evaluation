package com.matteo.projects.algo_evaluation.model;

import java.util.List;
import java.util.Objects;

public class Dataset {

	private String id;
	private String name;
	private List<Integer> integers;
	
	public Dataset(String id, String name, List<Integer> integers) {
		this.id = id;
		this.name = name;
		this.integers = integers;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getIntegers() {
		return integers;
	}

	@Override
	public String toString() {
		return "Dataset [id=" + id + ", name=" + name + ", integers=" + integers + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, integers, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dataset other = (Dataset) obj;
		return Objects.equals(id, other.id) && Objects.equals(integers, other.integers)
				&& Objects.equals(name, other.name);
	}

}
