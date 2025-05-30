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

    @Cacheable(cacheNames = "forecast-all-municipalities")
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
                        log.error("Could not build forecast for: {}", municipalityId, ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return new ForecastForAllMunicipalitiesDto(forecasts);
    }
}