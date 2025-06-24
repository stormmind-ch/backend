package com.stormmind.infrastructure.ai;

import ai.djl.repository.zoo.ModelNotFoundException;
import com.stormmind.application.ai.ModelInferenceService;
import com.stormmind.application.ai.ModelInferenceServiceProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ModelInferenceServiceFactory implements ModelInferenceServiceProvider {


    private final Map<String, ModelInferenceService> cache = new ConcurrentHashMap<>();

    @Override
    public ModelInferenceService getModelInferenceService(String modelName, ClusterSize clusterSize) {
        String key = modelName.toUpperCase() + "-" + clusterSize.name();
        return cache.computeIfAbsent(key, k -> {
            try {
                return switch (modelName.toUpperCase()) {
                    case "FNN" -> new FNNModelInferenceService(clusterSize);
                    case "LSTM" -> new LSTMModelInferenceService(clusterSize);
                    case "TRANSFORMER" -> new TransformerModelInferenceService(clusterSize);
                    default -> throw new ModelNotFoundException("No model found with name: " + modelName);
                };
            } catch (Exception e) {
                throw new RuntimeException("Could not create model service", e);
            }
        });
    }

}

