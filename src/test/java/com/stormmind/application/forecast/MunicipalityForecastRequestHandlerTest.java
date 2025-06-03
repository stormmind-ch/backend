package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.municipality.MunicipalityPort;
import com.stormmind.application.municipality.MunicipalityToClusterPort;
import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MunicipalityForecastRequestHandlerTest {

    private MunicipalityToClusterPort municipalityToClusterPort;
    private MunicipalityPort municipalityPort;
    private MunicipalityForecastRequestHandler handler;

    @BeforeEach
    void setUp() {
        municipalityToClusterPort = mock(MunicipalityToClusterPort.class);
        municipalityPort = mock(MunicipalityPort.class);
        handler = new MunicipalityForecastRequestHandler(municipalityToClusterPort, municipalityPort);
    }

    @Test
    void testDoHandle_setsMunicipalitiesCorrectly() throws Exception {
        // Arrange
        Municipality queried = new Municipality("Queried", new Coordinates(1.0f, 2.0f));
        Municipality target = new Municipality("Target", new Coordinates(3.0f, 4.0f));
        Municipality center = new Municipality("Center", new Coordinates(5.0f, 6.0f));
        MunicipalityToCluster mapping = new MunicipalityToCluster("Target", "Center");

        ForecastRequest request = new ForecastRequest();
        request.setQueriedMunicipality(queried.getName());

        when(municipalityToClusterPort.findByMunicipality(queried.getName())).thenReturn(mapping);
        when(municipalityPort.findByName("Target")).thenReturn(target);
        when(municipalityPort.findByName("Center")).thenReturn(center);

        // Act
        handler.handle(request);

        // Assert
        assertEquals(target, request.getTargetMunicipality());
        assertEquals(center, request.getCentroidMunicipality());

        verify(municipalityToClusterPort).findByMunicipality(queried.getName());
        verify(municipalityPort).findByName("Target");
        verify(municipalityPort).findByName("Center");
    }

    @Test
    void testDoHandle_throwsExceptionIfMappingNotFound() {
        // Arrange
        Municipality queried = new Municipality("Unknown", new Coordinates(0.0f, 0.0f));
        ForecastRequest request = new ForecastRequest();
        request.setQueriedMunicipality(queried.getName());

        when(municipalityToClusterPort.findByMunicipality(queried.getName())).thenReturn(null);

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> handler.handle(request));
        assertTrue(exception.getMessage().contains("Mapping for municipality"));

        verify(municipalityToClusterPort).findByMunicipality(queried.getName());
        verifyNoMoreInteractions(municipalityPort);
    }
}