package com.stormmind.application.ai;


import ai.djl.repository.zoo.ModelNotFoundException;
import com.stormmind.infrastructure.ai.ClusterSize;

/** Output-port that returns the predictor that matches a model name. */
public interface ModelInferenceServiceProvider {
    ModelInferenceService getModelInferenceService(String modelName, ClusterSize clusterSize) throws ModelNotFoundException;
}
