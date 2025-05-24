package com.stormmind.infrastructure.services;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.domain.*;
import com.stormmind.infrastructure.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import com.stormmind.infrastructure.services.persistence.MunicipalityService;
import com.stormmind.infrastructure.services.persistence.MunicipalityToClusterService;
import com.stormmind.infrastructure.weather_api.OpenMeteoWeatherFetcherFactory;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ForecastService {

    private final ModelInferenceServiceFactory modelInferenceServiceFactory;
    private final MunicipalityToClusterService municipalityToClusterService;
    private final MunicipalityService municipalityService;
    private final OpenMeteoWeatherFetcherFactory openMeteoWeatherFetcherFactory;


    public ForecastService(ModelInferenceServiceFactory modelInferenceServiceFactory,
                           MunicipalityToClusterService municipalityToClusterService,
                           MunicipalityService municipalityService,
                           OpenMeteoWeatherFetcherFactory openMeteoWeatherFetcherFactory) {
        this.modelInferenceServiceFactory = modelInferenceServiceFactory;
        this.municipalityToClusterService = municipalityToClusterService;
        this.municipalityService = municipalityService;
        this.openMeteoWeatherFetcherFactory = openMeteoWeatherFetcherFactory;
    }

    public float getForecast(String model, String  queriedMunicipality) throws TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        MunicipalityToCluster6 municipalityToCluster6 = municipalityToClusterService.getMunicipalityToClusterByMunicipality(queriedMunicipality);
        if (municipalityToCluster6 == null){
            throw new IOException("Mapping for municipality " + queriedMunicipality + " not found");
        }
        Municipality targetMunicipality = municipalityService.getMunicipalityById(queriedMunicipality);
        Municipality centroidMunicipality = municipalityService.getMunicipalityById(municipalityToCluster6.getCenter());
        WeatherDataDTO weatherDataDTO = openMeteoWeatherFetcherFactory.getWeatherFetcher(model).fetch(targetMunicipality, centroidMunicipality);
        ArrayList<WeatherValueDTO> temp = weatherDataDTO.forecast();

        // Inference Model
        Inference fnnModelPrompt = new FNNModelInference(0.0f,100000f, 0.7f);
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return modelInferenceService.predict(fnnModelPrompt);
    }
}