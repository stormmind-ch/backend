package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FNNFetcherTest {

    private OpenMeteoWeatherWeatherFetcherCurrentWeek fetcher;

    @BeforeEach
    void setUp() {
        fetcher = new OpenMeteoWeatherWeatherFetcherCurrentWeek();
    }

    @Test
    void testVanillaFetch() {
        // Beispiel:
        // WeatherDataDTO result = fetcher.vanillaFetch();
        // assertNotNull(result);
    }
}
