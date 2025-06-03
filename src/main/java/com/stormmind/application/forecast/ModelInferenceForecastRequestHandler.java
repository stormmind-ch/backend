package com.stormmind.application.forecast;

import com.stormmind.application.WeatherDataDtoToInferenceService;
import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.domain.Forecast;
import com.stormmind.domain.Inference;
import com.stormmind.application.ai.ModelInferenceService;
import com.stormmind.infrastructure.ai.ModelInferenceServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(30)
public class ModelInferenceForecastRequestHandler extends AbstractForecastHandler{

    private final ModelInferenceServiceFactory modelInferenceServiceFactory;

    @Override
    protected void doHandle(ForecastRequest forecastRequest) throws Exception {
        ModelInferenceService modelInferenceService = modelInferenceServiceFactory.getModelInferenceService(forecastRequest.getModel());
        Inference inference = WeatherDataDtoToInferenceService.weatherDataDTOToInference(forecastRequest.getWeatherData());
        float prediction = modelInferenceService.predict(inference);
        forecastRequest.setForecast(new Forecast(prediction, forecastRequest.getTargetMunicipality()));
    }
}
