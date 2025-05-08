package com.stormmind;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.infrastructure.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ForcastController {

    private final ModelInferenceServiceFactory modelInferenceServiceFactory;

    public ForcastController(ModelInferenceServiceFactory modelInferenceServiceFactory){
        this.modelInferenceServiceFactory = modelInferenceServiceFactory;
    }

    @GetMapping("/{model}/{municipality}")
    private float[] getForcast(@PathVariable String model, @PathVariable String municipality) throws ModelNotFoundException, TranslateException, MalformedModelException, IOException {
        // TODO Get Cluster Centroid
        // TODO Get Coordinates for Cluster Centroid
        // TODO Get Weather for Cluster Centroid
        // Inference Model
        float[] TEMPWEATHERDATA = new float[] {10f, 0.2f, 0.7f};
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return modelInferenceService.predict(TEMPWEATHERDATA);
    }
}
