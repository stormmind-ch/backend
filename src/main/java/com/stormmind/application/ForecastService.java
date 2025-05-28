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
import com.stormmind.infrastructure.weather_api.WeatherFetcher;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.response.forecast.ForecastDto;
import com.stormmind.presentation.dtos.response.forecast.ForecastForAllMunicipalitiesDto;
import org.springframework.cache.annotation.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
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

    public ForecastDto getForecast(String model, String  queriedMunicipality) throws TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        MunicipalityToCluster municipalityToCluster = municipalityToClusterService.getMunicipalityToClusterByMunicipality(queriedMunicipality);
        if (municipalityToCluster == null){
            throw new IOException("Mapping for municipality " + queriedMunicipality + " not found");
        }
        Municipality targetMunicipality = municipalityService.getMunicipalityById(queriedMunicipality);
        Municipality centroidMunicipality = municipalityService.getMunicipalityById(municipalityToCluster.getCenter());
        WeatherFetcher weatherFetcher = openMeteoWeatherFetcherFactory.getWeatherFetcher(model);
        if (weatherFetcher == null){
            log.error("Model was not found with name: {}", model);
            throw new IOException("Model not found with name " + model);
        }
        WeatherDataDTO weatherDataDTO = weatherFetcher.fetch(targetMunicipality, centroidMunicipality);
        Inference fnnModelPrompt = WeatherDataDtoToInferenceService.weatherDataDTOToInference(weatherDataDTO);
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        return new ForecastDto(targetMunicipality, modelInferenceService.predict(fnnModelPrompt));
    }

    @Cacheable("forecast")
    public ForecastForAllMunicipalitiesDto getForecastForAllMunicipalities(String model) throws IOException, ModelNotFoundException, TranslateException, MalformedModelException {
        // 1. Get all mappings: Municipality -> Cluster
        List<MunicipalityToCluster> mappings = municipalityToClusterService.getAllMunicipalitiesToCluster();

        // 2. Deduplicate cluster centers
        Map<String, Municipality> clusterIdToMunicipality = new HashMap<>();
        for (MunicipalityToCluster mapping : mappings) {
            String clusterId = mapping.getCenter();
            clusterIdToMunicipality.computeIfAbsent(clusterId, municipalityService::getMunicipalityById);
        }

        // 3. Fetch weather for each cluster (clusterId -> WeatherDataDTO)
        WeatherFetcher weatherFetcher = openMeteoWeatherFetcherFactory.getWeatherFetcher(model);
        if (weatherFetcher == null) {
            log.error("Model was not found with name: {}", model);
            throw new IOException("Model not found with name " + model);
        }

        Map<String, WeatherDataDTO> clusterWeatherMap = new HashMap<>();
        for (Map.Entry<String, Municipality> entry : clusterIdToMunicipality.entrySet()) {
            String clusterId = entry.getKey();
            Municipality cluster = entry.getValue();
            clusterWeatherMap.put(clusterId, weatherFetcher.fetch(cluster, cluster));
        }

        // 4. Run inference for each cluster
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(model);
        Map<String, Float> clusterForecastMap = new HashMap<>();
        for (Map.Entry<String, WeatherDataDTO> entry : clusterWeatherMap.entrySet()) {
            Inference inferenceInput = WeatherDataDtoToInferenceService.weatherDataDTOToInference(entry.getValue());
            clusterForecastMap.put(entry.getKey(), modelInferenceService.predict(inferenceInput));
        }

        // 5. Build ForecastDto for every municipality using its cluster’s forecast
        List<ForecastDto> forecasts = new ArrayList<>();
        for (MunicipalityToCluster mapping : mappings) {
            String municipalityId = mapping.getMunicipality();
            Municipality municipality = municipalityService.getMunicipalityById(municipalityId);
            String clusterId = mapping.getCenter();
            float forecastValue = clusterForecastMap.get(clusterId);
            forecasts.add(new ForecastDto(municipality, forecastValue)); // Assuming ForecastDto has a constructor like that
        }
        return new ForecastForAllMunicipalitiesDto(forecasts);
    }

}