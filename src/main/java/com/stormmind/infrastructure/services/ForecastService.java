package com.stormmind.infrastructure.services;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.domain.*;
import com.stormmind.infrastructure.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import com.stormmind.infrastructure.services.persistence.MunicipalityService;
import com.stormmind.infrastructure.services.persistence.MunicipalityToClusterService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ForecastService {

    private final ModelInferenceServiceFactory modelInferenceServiceFactory;
    private final MunicipalityToClusterService municipalityToClusterService;
    private final MunicipalityService municipalityService;


    public ForecastService(ModelInferenceServiceFactory modelInferenceServiceFactory,
                           MunicipalityToClusterService municipalityToClusterService,
                           MunicipalityService municipalityService) {
        this.modelInferenceServiceFactory = modelInferenceServiceFactory;
        this.municipalityToClusterService = municipalityToClusterService;
        this.municipalityService = municipalityService;
    }

    public float getForecast(String model, String  queriedMunicipality) throws TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        // Get Nr of clusters for Model
        //String file = modelToClustersLookupService.getClusterFile(model);
        MunicipalityToCluster6 municipalityToCluster6 = municipalityToClusterService.getMunicipalityToClusterByMunicipality(queriedMunicipality);
        if (municipalityToCluster6 == null){
            System.out.println("No mapping found for " + queriedMunicipality);
        }
        Municipality municipality = municipalityService.getMunicipalityById(municipalityToCluster6.getCenter());


        // TODO Get Weather for Cluster Centroid

        // Inference Model
        AIPrompt fnnModelPrompt = new FNNModelPrompt(0.0f,100000f, 0.7f);
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return modelInferenceService.predict(fnnModelPrompt);
    }
}