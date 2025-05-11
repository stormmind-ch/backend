package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SydneySweeneyFetcherTest {

    private SydneySweeneyFetcher sydney;

    @BeforeEach
    void setUp() {
        sydney = new SydneySweeneyFetcher();
    }

    @Test
    void testSomething() {
        // assertEquals(...);
    }
}
