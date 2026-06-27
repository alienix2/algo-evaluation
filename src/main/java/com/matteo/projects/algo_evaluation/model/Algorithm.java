package com.matteo.projects.algo_evaluation.model;

import java.util.Objects;

public class Algorithm {
	private String id;
	private String name;

	public Algorithm(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Algorithm [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Algorithm other = (Algorithm) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
