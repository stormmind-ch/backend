package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.municipality.MunicipalityPort;
import com.stormmind.application.municipality.MunicipalityToClusterPort;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster;
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

    private final MunicipalityToClusterPort municipalityToClusterPort;
    private final MunicipalityPort municipalityPort;


    @Override
    protected void doHandle(ForecastRequest forecastRequest) throws IOException {
        MunicipalityToCluster municipalityToCluster = municipalityToClusterPort.findByMunicipality(forecastRequest.getQueriedMunicipality());
        if (municipalityToCluster == null){
            throw new IOException("Mapping for municipality " + forecastRequest.getQueriedMunicipality() + " not found");
        }
        Municipality targetMunicipality = municipalityPort.findByName(municipalityToCluster.getMunicipality());
        Municipality centerMunicipality = municipalityPort.findByName(municipalityToCluster.getCenter());
        forecastRequest.setTargetMunicipality(targetMunicipality);
        forecastRequest.setCentroidMunicipality(centerMunicipality);

    }
}
