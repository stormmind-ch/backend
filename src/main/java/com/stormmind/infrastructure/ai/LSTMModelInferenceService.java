package com.stormmind.infrastructure.ai;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import com.stormmind.application.ai.ModelInferenceService;
import com.stormmind.domain.Inference;
import jakarta.annotation.PreDestroy;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.nio.file.Paths;

// TODO, cannot be done until we don't have a working LSTM

public class LSTMModelInferenceService implements ModelInferenceService {

    private final ClusterSize clusterSize;
    private ZooModel<Inference, Float> model;

    public LSTMModelInferenceService(ClusterSize clusterSize)throws ModelNotFoundException, MalformedModelException, IOException {
        this.clusterSize = clusterSize;
        String modelName;
        if (clusterSize == ClusterSize.THREE){
            modelName = "LSTM-3";
        }else{
            modelName = "LSTM-6";
        }
        Criteria<Inference, Float> criteria = Criteria.builder()
                .setTypes(Inference.class, Float.class)
                .optModelPath(Paths.get("models"))
                .optModelName(modelName)
                .optTranslator(new ModelTranslator(clusterSize))
                .build();

        model = ModelZoo.loadModel(criteria);
    }

    /**
     * This method is used that the memory is freed after the application shuts down, as some parts of the DJL modules
     * are not managed by the Java GC.
     */
    @PreDestroy
    public void cleanup() {
        if (model != null) {
            model.close();
        }
    }

    @Override
    @Cacheable(cacheNames = "inference")
    public float predict(Inference inputData) throws TranslateException {
        try (Predictor<Inference, Float> predictor = model.newPredictor()) {
            return predictor.predict(inputData);
        }
    }
}
