package com.matteo.projects.algo_evaluation.algorithm;

import java.util.HashMap;
import java.util.Map;

public class SortingAlgorithmRegistry {
    private Map<String, SortingAlgorithm> algorithmMap = new HashMap<>();

    public void register(String name, SortingAlgorithm algorithm) {
        algorithmMap.put(name, algorithm);
    }

    public SortingAlgorithm get(String name) {
        return algorithmMap.get(name);
    }
}
