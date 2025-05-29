package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreviousYearWeatherFetcherTest {

    private OpenMeteoWeatherWeatherFetcherPreviousYearDecorator openMeteoWeatherFetcherPreviousYearDecorator;

    @BeforeEach
    void setUp() {
        openMeteoWeatherFetcherPreviousYearDecorator = new OpenMeteoWeatherWeatherFetcherPreviousYearDecorator(new OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator(new OpenMeteoWeatherWeatherFetcherCurrentWeek()));
    }

    @Test
    void testSomething() {
        // assertTrue(...);
    }
}
