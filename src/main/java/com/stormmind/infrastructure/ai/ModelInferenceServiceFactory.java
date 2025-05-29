package com.stormmind.infrastructure.ai;

import ai.djl.repository.zoo.ModelNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ModelInferenceServiceFactory {

    private final FNNModelInferenceService fnnModelInferenceService;
    private final LSTMModelInfereneceService lstmModelInfereneceService;

    public ModelInferenceServiceFactory(
            FNNModelInferenceService fnnModelInferenceService,
            LSTMModelInfereneceService lstmModelInfereneceService
    ) {
        this.fnnModelInferenceService = fnnModelInferenceService;
        this.lstmModelInfereneceService = lstmModelInfereneceService;
    }

    public ModelInferenceService getModelInferenceService(String modelName) throws ModelNotFoundException {
        return switch (modelName) {
            case "FNN" -> fnnModelInferenceService;
            case "LSTM" -> lstmModelInfereneceService;
            default -> throw new ModelNotFoundException("No model found with name: " + modelName);
        };
    }
}

