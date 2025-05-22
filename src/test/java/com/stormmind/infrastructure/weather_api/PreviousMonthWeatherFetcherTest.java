package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreviousMonthWeatherFetcherTest {

    private PreviousMonthWeatherFetcher sydney;

    @BeforeEach
    void setUp() {
        sydney = new PreviousMonthWeatherFetcher(new FNNFetcher());
    }

    @Test
    void testSomething() {
        // assertEquals(...);
    }
}
