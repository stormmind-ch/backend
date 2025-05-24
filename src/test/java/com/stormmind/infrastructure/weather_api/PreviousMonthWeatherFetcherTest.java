package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreviousMonthWeatherFetcherTest {

    private OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator sydney;

    @BeforeEach
    void setUp() {
        sydney = new OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator(new OpenMeteoWeatherWeatherFetcherCurrentWeek());
    }

    @Test
    void testSomething() {
        // assertEquals(...);
    }
}
