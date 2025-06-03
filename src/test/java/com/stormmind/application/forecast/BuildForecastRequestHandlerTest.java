package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Forecast;
import com.stormmind.domain.Municipality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildForecastRequestHandlerTest {

    private BuildForecastRequestHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BuildForecastRequestHandler();
    }

    @Test
    void testDoHandle_setsForecastCorrectly() throws Exception {
        // Arrange
        Forecast forecast = new Forecast( 0.4f, new Municipality("ort", new Coordinates( 11, 22)));
        ForecastRequest request = new ForecastRequest();
        request.setForecast(forecast);

        // Act
        handler.handle(request);

        // Assert
        assertSame(forecast, request.getForecast(), "Forecast should remain unchanged after handling");
    }

    @Test
    void testDoHandle_withNullForecast_doesNotThrow() {
        // Arrange
        ForecastRequest request = new ForecastRequest();
        request.setForecast(null);

        // Act & Assert
        assertDoesNotThrow(() -> handler.handle(request), "Handler should handle null forecast gracefully");
        assertNull(request.getForecast(), "Forecast should remain null");
    }
}