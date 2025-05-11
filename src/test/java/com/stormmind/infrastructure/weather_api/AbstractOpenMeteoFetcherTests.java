package com.stormmind.infrastructure.weather_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractOpenMeteoFetcherTest {

    private AbstractOpenMeteoFetcher fetcher;

    @BeforeEach
    void setUp() {
        // fetcher ist abstrakt – kann nicht direkt instanziert werden
        // ggf. anlegen als anonyme Klasse oder über konkreten Subtyp testen
    }

    @Test
    void testSomething() {
        // assertEquals(...);
    }
}