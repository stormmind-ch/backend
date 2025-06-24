package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.municipality.MunicipalityPort;
import com.stormmind.application.municipality.MunicipalityToClusterPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MunicipalityForecastRequestHandlerTest {

    @Mock
    private MunicipalityToClusterPort municipalityToClusterPort;

    @Mock
    private MunicipalityPort municipalityPort;

    @InjectMocks
    private MunicipalityForecastRequestHandler handler;

    /*
    @Test
    void testDoHandle_setsMunicipalitiesCorrectly() throws Exception {
        Municipality queried = new Municipality("Queried", new Coordinates(1.0f, 2.0f));
        Municipality target = new Municipality("Target", new Coordinates(3.0f, 4.0f));
        Municipality center = new Municipality("Center", new Coordinates(5.0f, 6.0f));
        MunicipalityToCluster mapping = new MunicipalityToCluster("Queried", "Target", "Center");

        ForecastRequest request = new ForecastRequest();
        request.setQueriedMunicipality("Queried");

        when(municipalityToClusterPort.findByMunicipality("Queried")).thenReturn(mapping);
        when(municipalityPort.findByName("Target")).thenReturn(target);
        when(municipalityPort.findByName("Center")).thenReturn(center);

        handler.handle(request);

        assertEquals(target, request.getTargetMunicipality());
        assertEquals(center, request.getCentroidMunicipality());

        verify(municipalityToClusterPort).findByMunicipality("Queried");
        verify(municipalityPort).findByName("Target");
        verify(municipalityPort).findByName("Center");
    }

     */

    @Test
    void testDoHandle_throwsExceptionIfMappingNotFound() {
        ForecastRequest request = new ForecastRequest();
        request.setQueriedMunicipality("Unknown");

        when(municipalityToClusterPort.findByMunicipality("Unknown")).thenReturn(null);

        IOException exception = assertThrows(IOException.class, () -> handler.handle(request));
        assertTrue(exception.getMessage().contains("Mapping for municipality"));

        verify(municipalityToClusterPort).findByMunicipality("Unknown");
        verifyNoMoreInteractions(municipalityPort);
    }
}
