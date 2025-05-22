package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FNNFetcherTest {

    private FNNFetcher fetcher;

    @BeforeEach
    void setUp() {
        fetcher = new FNNFetcher();
    }

    @Test
    void testVanillaFetch() {
        // Beispiel:
        // WeatherDataDTO result = fetcher.vanillaFetch();
        // assertNotNull(result);
    }
}
