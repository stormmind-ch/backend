package com.stormmind.infrastructure.weather_api;

import com.stormmind.application.weather.WeatherFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OpenMeteoWeatherFetcherFactoryTest {

    private WeatherFetcher mockFetcherFNN;
    private OpenMeteoWeatherFetcherFactory factory;

    @BeforeEach
    void setUp() {
        mockFetcherFNN = mock(WeatherFetcher.class);
        factory = new OpenMeteoWeatherFetcherFactory(Map.of(
                "FNN", mockFetcherFNN
        ));
    }

    @Test
    void testGetWeatherFetcher_returnsCorrectFetcher() {
        // Act
        WeatherFetcher result = factory.getWeatherFetcher("FNN");

        // Assert
        assertNotNull(result);
        assertSame(mockFetcherFNN, result);
    }

    @Test
    void testGetWeatherFetcher_unknownModel_returnsNull() {
        // Act
        WeatherFetcher result = factory.getWeatherFetcher("UNKNOWN");

        // Assert
        assertNull(result);
    }
}