package com.stormmind.application;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.forecast.ForecastRequestHandler;
import com.stormmind.domain.MunicipalityToCluster;
import com.stormmind.infrastructure.services.persistence.MunicipalityToClusterService;
import com.stormmind.presentation.dtos.response.forecast.ForecastDto;
import com.stormmind.presentation.dtos.response.forecast.ForecastForAllMunicipalitiesDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForecastService {



    private final ForecastRequestHandler head;
    private final List<ForecastRequestHandler> chain;
    private final MunicipalityToClusterService municipalityToClusterService;

    @PostConstruct
    void wireChain() {
        for (int i = 0; i < chain.size() - 1; i++) {
            chain.get(i).setNext(chain.get(i + 1));
        }
    }


    public ForecastDto getForecast(String model, String  queriedMunicipality) throws Exception {
        ForecastRequest forecastRequest = new ForecastRequest();
        forecastRequest.setModel(model);
        forecastRequest.setQueriedMunicipality(queriedMunicipality);
        head.handle(forecastRequest);
        return forecastRequest.getResult();
    }

    @Cacheable("forecast")
    public ForecastForAllMunicipalitiesDto getForecastForAllMunicipalities(String model) throws Exception {

        List<MunicipalityToCluster> mappings =
                municipalityToClusterService.getAllMunicipalitiesToCluster();

        List<ForecastDto> forecasts = mappings.stream()
                .map(MunicipalityToCluster::getMunicipality)
                .parallel()
                .map(municipalityId -> {
                    try {
                        return getForecast(model, municipalityId);
                    } catch (Exception ex) {
                        log.error("Could not build forecast for {}", municipalityId, ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return new ForecastForAllMunicipalitiesDto(forecasts);
    }


    /*
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
     */



}