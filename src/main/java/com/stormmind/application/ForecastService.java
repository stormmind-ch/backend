package com.stormmind.application;

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
import org.springframework.stereotype.Service;
import java.io.IOException;

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
        MunicipalityToCluster municipalityToCluster = municipalityToClusterService.getMunicipalityToClusterByMunicipality(queriedMunicipality);
        if (municipalityToCluster == null){
            throw new IOException("Mapping for municipality " + queriedMunicipality + " not found");
        }
        Municipality targetMunicipality = municipalityService.getMunicipalityById(queriedMunicipality);
        Municipality centroidMunicipality = municipalityService.getMunicipalityById(municipalityToCluster.getCenter());
        WeatherDataDTO weatherDataDTO = openMeteoWeatherFetcherFactory.getWeatherFetcher(model).fetch(targetMunicipality, centroidMunicipality);
        Inference fnnModelPrompt = WeatherDataDtoToInferenceService.weatherDataDTOToInference(weatherDataDTO);
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return modelInferenceService.predict(fnnModelPrompt);
    }
}