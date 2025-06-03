package com.stormmind.application.forecast;

import com.stormmind.application.WeatherDataDtoToInferenceService;
import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.domain.*;
import com.stormmind.application.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ModelInferenceForecastRequestHandlerTest {

    private ModelInferenceServiceFactory modelInferenceServiceFactory;
    private ModelInferenceService modelInferenceService;
    private ModelInferenceForecastRequestHandler handler;

    @BeforeEach
    void setUp() {
        modelInferenceServiceFactory = mock(ModelInferenceServiceFactory.class);
        modelInferenceService = mock(ModelInferenceService.class);
        handler = new ModelInferenceForecastRequestHandler(modelInferenceServiceFactory);
    }

    @Test
    void testDoHandle_setsForecastCorrectly() throws Exception {
        // Arrange
        String modelName = "test-model";
        float predictedValue = 42.0f;
        WeatherData weatherData = new WeatherData("mun", "cent", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Inference inference = WeatherDataDtoToInferenceService.weatherDataDTOToInference(weatherData); // Dummy-Inference
        Municipality municipality = new Municipality("TestTown", new Coordinates(1.0f, 2.0f));

        ForecastRequest request = new ForecastRequest();
        request.setModel(modelName);
        request.setWeatherData(weatherData);
        request.setTargetMunicipality(municipality);

        // Static mocking: WeatherDataDtoToInferenceService.weatherDataDTOToInference(...)
        try (MockedStatic<WeatherDataDtoToInferenceService> mockedStatic = mockStatic(WeatherDataDtoToInferenceService.class)) {
            mockedStatic.when(() -> WeatherDataDtoToInferenceService.weatherDataDTOToInference(weatherData))
                    .thenReturn(inference);

            when(modelInferenceServiceFactory.getModelInferenceService(modelName)).thenReturn(modelInferenceService);
            when(modelInferenceService.predict(inference)).thenReturn(predictedValue);

            // Act
            handler.handle(request);

            // Assert
            Forecast forecast = request.getForecast();
            assertNotNull(forecast);
            assertEquals(predictedValue, forecast.getForecast(), 0.0001f);
            assertEquals(municipality, forecast.getMunicipality());

            // Verify interactions
            verify(modelInferenceServiceFactory).getModelInferenceService(modelName);
            verify(modelInferenceService).predict(inference);
        }
    }
}