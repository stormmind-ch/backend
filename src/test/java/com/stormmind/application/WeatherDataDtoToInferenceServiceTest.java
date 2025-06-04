package com.stormmind.application;

import com.stormmind.domain.FNNModelInference;
import com.stormmind.domain.Inference;
import com.stormmind.domain.WeatherData;
import com.stormmind.domain.WeatherValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataDtoToInferenceServiceTest {

    @Test
    void testWeatherDataDTOToInference_calculatesCorrectValues() {
        // Arrange
        WeatherValue w1 = new WeatherValue(
                List.of(10.0, 12.0),  // Temp
                List.of(5.0, 7.0),   // Sun
                List.of(2.0, 3.0),    // Rain
                List.of()            // Snow -> not used anymore
        );

        WeatherValue w2 = new WeatherValue(
                List.of(14.0, 16.0),  // Temp
                List.of(8.0, 6.0),    // Sun
                List.of(1.0, 4.0),    // Rain
                List.of()            // Snow -> not used anymore
        );

        WeatherData data = new WeatherData("mun","cent",new ArrayList<>(List.of(w1, w2)),new ArrayList<>(),new ArrayList<>());

        // Erwartete Werte:
        // Temp: (10+12+14+16)/4 = 13.0
        // Sun:  (5+7+8+6)/4 = 6.5
        // Rain: 2+3+1+4 = 10.0

        // Act
        Inference result = WeatherDataDtoToInferenceService.weatherDataDTOToInference(data);

        // Assert
        assertTrue(result instanceof FNNModelInference);
        FNNModelInference fnn = (FNNModelInference) result;

        assertEquals(13.0f, fnn.temperature_mean(), 0.0001f);
        assertEquals(6.5f, fnn.sun_mean(), 0.0001f);
        assertEquals(10.0f, fnn.rain_sum(), 0.0001f);
    }

    @Test
    void testWeatherDataDTOToInference_handlesEmptyInput() {
        // Arrange
        WeatherValue empty = new WeatherValue(List.of(), List.of(), List.of(), List.of());
        WeatherData data = new WeatherData("mun","cent",new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        // Act
        Inference result = WeatherDataDtoToInferenceService.weatherDataDTOToInference(data);

        // Assert
        FNNModelInference fnn = (FNNModelInference) result;
        assertEquals(0.0f, fnn.temperature_mean());
        assertEquals(0.0f, fnn.sun_mean());
        assertEquals(0.0f, fnn.rain_sum());
    }
}