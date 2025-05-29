package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.infrastructure.weather_api.OpenMeteoWeatherFetcherFactory;
import com.stormmind.infrastructure.weather_api.WeatherFetcher;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(20)
public class WeatherForecastRequestHandler extends AbstractForecastHandler{

    private final OpenMeteoWeatherFetcherFactory openMeteoWeatherFetcherFactory;

    @Override
    protected void doHandle(ForecastRequest forecastRequest) throws Exception {
        WeatherFetcher weatherFetcher = openMeteoWeatherFetcherFactory.getWeatherFetcher(forecastRequest.getModel());
        if (weatherFetcher == null){
            log.error("Model was not found with name: {}", forecastRequest.getModel());
            throw new IOException("Model not found with name " + forecastRequest.getModel());
        }
        WeatherDataDTO weatherDataDTO = weatherFetcher.fetch(forecastRequest.getTargetMunicipality(), forecastRequest.getCentroidMunicipality());
        forecastRequest.setWeatherData(weatherDataDTO);
    }
}
