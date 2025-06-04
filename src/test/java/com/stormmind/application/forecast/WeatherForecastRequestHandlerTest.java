package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.weather.WeatherFetcher;
import com.stormmind.application.weather.WeatherFetcherProvider;
import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastRequestHandlerTest {

    private WeatherFetcherProvider weatherFetcherProvider;
    private WeatherFetcher weatherFetcher;
    private WeatherForecastRequestHandler handler;

    @BeforeEach
    void setUp() {
        weatherFetcherProvider = mock(WeatherFetcherProvider.class);
        weatherFetcher = mock(WeatherFetcher.class);
        handler = new WeatherForecastRequestHandler(weatherFetcherProvider);
    }

    @Test
    void testDoHandle_setsWeatherDataCorrectly() throws Exception {
        // Arrange
        String modelName = "test-model";
        Municipality target = new Municipality("Target", new Coordinates(1.0f, 2.0f));
        Municipality centroid = new Municipality("Centroid", new Coordinates(3.0f, 4.0f));
        WeatherData weatherData = new WeatherData("mun", "cent",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()); // oder Dummy-Daten

        ForecastRequest request = new ForecastRequest();
        request.setModel(modelName);
        request.setTargetMunicipality(target);
        request.setCentroidMunicipality(centroid);

        when(weatherFetcherProvider.getWeatherFetcher(modelName)).thenReturn(weatherFetcher);
        when(weatherFetcher.fetch(target, centroid)).thenReturn(weatherData);

        // Act
        handler.handle(request);

        // Assert
        assertEquals(weatherData, request.getWeatherData());
        verify(weatherFetcherProvider).getWeatherFetcher(modelName);
        verify(weatherFetcher).fetch(target, centroid);
    }

    @Test
    void testDoHandle_throwsIOExceptionIfModelNotFound() {
        // Arrange
        String modelName = "unknown-model";
        ForecastRequest request = new ForecastRequest();
        request.setModel(modelName);

        when(weatherFetcherProvider.getWeatherFetcher(modelName)).thenReturn(null);

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> handler.handle(request));
        assertTrue(exception.getMessage().contains("Model not found with name " + modelName));
        verify(weatherFetcherProvider).getWeatherFetcher(modelName);
    }
}