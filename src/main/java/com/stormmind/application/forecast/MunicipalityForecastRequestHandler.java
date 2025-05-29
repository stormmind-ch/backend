package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster;
import com.stormmind.infrastructure.services.persistence.MunicipalityService;
import com.stormmind.infrastructure.services.persistence.MunicipalityToClusterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(10)
@Primary
public class MunicipalityForecastRequestHandler extends AbstractForecastHandler{

    private final MunicipalityToClusterService municipalityToClusterService;
    private final MunicipalityService municipalityService;


    @Override
    protected void doHandle(ForecastRequest forecastRequest) throws IOException {
        MunicipalityToCluster municipalityToCluster = municipalityToClusterService.getMunicipalityToClusterByMunicipality(forecastRequest.getQueriedMunicipality());
        if (municipalityToCluster == null){
            throw new IOException("Mapping for municipality " + forecastRequest.getQueriedMunicipality() + " not found");
        }
        Municipality targetMunicipality = municipalityService.getMunicipalityById(municipalityToCluster.getMunicipality());
        Municipality centerMunicipality = municipalityService.getMunicipalityById(municipalityToCluster.getCenter());
        forecastRequest.setTargetMunicipality(targetMunicipality);
        forecastRequest.setCentroidMunicipality(centerMunicipality);

    }
}
