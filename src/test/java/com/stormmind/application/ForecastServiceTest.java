package com.stormmind.application;

import com.stormmind.application.forecast.ForecastRequestHandler;
import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.municipality.MunicipalityToClusterPort;
import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Forecast;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ForecastServiceTest {

    private ForecastRequestHandler headHandler;
    private MunicipalityToClusterPort municipalityToClusterPort;
    private ForecastService forecastService;

    @BeforeEach
    void setUp() {
        headHandler = mock(ForecastRequestHandler.class);
        municipalityToClusterPort = mock(MunicipalityToClusterPort.class);
        forecastService = new ForecastService(headHandler, List.of(headHandler), municipalityToClusterPort);
    }

    @Test
    void testGetForecast_returnsForecastFromHandler() throws Exception {
        // Arrange
        String model = "test-model";
        String municipalityId = "test-town";

        // Mock das Verhalten des Handlers: Forecast in ForecastRequest setzen
        doAnswer(invocation -> {
            ForecastRequest req = invocation.getArgument(0);
            req.setForecast(new Forecast(0.75f, new Municipality(municipalityId, new Coordinates(1, 2))));
            return null;
        }).when(headHandler).handle(any(ForecastRequest.class));

        // Act
        Forecast result = forecastService.getForecast(model, municipalityId);

        // Assert
        assertNotNull(result);
        assertEquals(0.75f, result.getForecast(), 0.0001);
        assertEquals(municipalityId, result.getMunicipality().getName());
    }

    @Test
    void testGetForecastForAllMunicipalities_handlesAllMappingsAndFiltersNulls() throws Exception {
        // Arrange
        String model = "test-model";
        Municipality munA = new Municipality("A", new Coordinates(1f, 2f));
        Municipality munB = new Municipality("B", new Coordinates(3f, 4f));

        MunicipalityToCluster mapping1 = new MunicipalityToCluster(munA.getName(), "cluster1");
        MunicipalityToCluster mapping2 = new MunicipalityToCluster(munB.getName(), "cluster2");
        when(municipalityToClusterPort.getAllMunicipalityToCluster()).thenReturn(List.of(mapping1, mapping2));

        ForecastService spyService = spy(forecastService);

        // getForecast() für A funktioniert
        Forecast forecastA = new Forecast(1.0f, munA);
        doReturn(forecastA).when(spyService).getForecast(model, "A");

        // getForecast() für B wirft Exception → soll zu null führen
        doThrow(new RuntimeException("fail")).when(spyService).getForecast(model, "B");

        // Act
        List<Forecast> result = spyService.getForecastForAllMunicipalities(model);

        // Assert
        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getMunicipality().getName());
    }
}