package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreviousYearWeatherFetcherTest {

    private PreviousYearWeatherFetcher previousYearWeatherFetcher;

    @BeforeEach
    void setUp() {
        previousYearWeatherFetcher = new PreviousYearWeatherFetcher(new PreviousMonthWeatherFetcher(new FNNFetcher()));
    }

    @Test
    void testSomething() {
        // assertTrue(...);
    }
}
