package com.stormmind.application.ai;


import ai.djl.repository.zoo.ModelNotFoundException;

/** Output-port that returns the predictor that matches a model name. */
public interface ModelInferenceServiceProvider {
    ModelInferenceService getModelInferenceService(String modelName) throws ModelNotFoundException;
}
