package com.stormmind.application;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.forecast.ForecastRequestHandler;
import com.stormmind.application.municipality.MunicipalityToClusterPort;
import com.stormmind.domain.Forecast;
import com.stormmind.domain.MunicipalityToCluster;
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
    private final MunicipalityToClusterPort municipalityToClusterPort;

    @PostConstruct
    void wireChain() {
        for (int i = 0; i < chain.size() - 1; i++) {
            chain.get(i).setNext(chain.get(i + 1));
        }
    }

    public Forecast getForecast(String model, String  queriedMunicipality) throws Exception {
        ForecastRequest forecastRequest = new ForecastRequest();
        forecastRequest.setModel(model);
        forecastRequest.setQueriedMunicipality(queriedMunicipality);
        head.handle(forecastRequest);
        return forecastRequest.getForecast();
    }

    @Cacheable(cacheNames = "forecast-all-municipalities")
    public List<Forecast> getForecastForAllMunicipalities(String model) {

        List<MunicipalityToCluster> mappings =
                municipalityToClusterPort.getAllMunicipalityToCluster();

        return mappings.stream()
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
    }
}