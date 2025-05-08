package com.stormmind.infrastructure.services;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.application.ModelToClustersLookupService;
import com.stormmind.application.MunicipalityToCentroidLookupService;
import com.stormmind.application.MunicipalityToCoordinatesLookupService;
import com.stormmind.domain.AIPrompt;
import com.stormmind.domain.Coordinates;
import com.stormmind.domain.FNNModelPrompt;
import com.stormmind.domain.Municipality;
import com.stormmind.infrastructure.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ForecastService {

    private final ModelInferenceServiceFactory modelInferenceServiceFactory;
    private final MunicipalityToCoordinatesLookupService municipalityToCoordinatesLookupService;
    private final ModelToClustersLookupService modelToClustersLookupService;
    private final MunicipalityToCentroidLookupService municipalityToCentroidLookupService;

    public ForecastService(ModelInferenceServiceFactory modelInferenceServiceFactory,
                           MunicipalityToCoordinatesLookupService municipalityToCoordinatesLookupService,
                           ModelToClustersLookupService modelToClustersLookupService, MunicipalityToCentroidLookupService municipalityToCentroidLookupService) {
        this.modelInferenceServiceFactory = modelInferenceServiceFactory;
        this.municipalityToCoordinatesLookupService = municipalityToCoordinatesLookupService;
        this.modelToClustersLookupService = modelToClustersLookupService;
        this.municipalityToCentroidLookupService = municipalityToCentroidLookupService;
    }

    public float getForecast(String model, String  queriedMunicipality) throws TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        // Get Nr of clusters for Model
        int nr_of_clusters = modelToClustersLookupService.getClusterFile(model);

        // TODO Get Cluster Centroid




        String clusterCenteroidMunicipalityName = "Affoltern am Albis";
        //  Get Coordinates for Cluster Centroid
        Coordinates coordinates = municipalityToCoordinatesLookupService.getCoordinatesForMunicipality(clusterCenteroidMunicipalityName);

        Municipality clusterCentroidMunicipality = new Municipality(clusterCenteroidMunicipalityName, coordinates);
        // TODO Get Weather for Cluster Centroid

        // Inference Model
        AIPrompt fnnModelPrompt = new FNNModelPrompt(0.0f,100000f, 0.7f);
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return modelInferenceService.predict(fnnModelPrompt);
    }
}