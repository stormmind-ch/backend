package com.stormmind.infrastructure.services;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.infrastructure.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ForecastService {

    private final ModelInferenceServiceFactory modelInferenceServiceFactory;

    public ForecastService(ModelInferenceServiceFactory modelInferenceServiceFactory) {
        this.modelInferenceServiceFactory = modelInferenceServiceFactory;
    }

    public float[] getForcast(String model, String  municipality) throws TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        // TODO Get Cluster Centroid
        // TODO Get Coordinates for Cluster Centroid
        // TODO Get Weather for Cluster Centroid
        // Inference Model
        float[] TEMPWEATHERDATA = new float[] {0.0f, 100000f, 0.7f};
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return modelInferenceService.predict(TEMPWEATHERDATA);
    }
}
