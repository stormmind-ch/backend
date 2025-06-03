package com.stormmind.application.forecast;

import com.stormmind.application.forecast.request.ForecastRequest;
import com.stormmind.application.weather.WeatherFetcherProvider;
import com.stormmind.domain.WeatherData;
import com.stormmind.application.weather.WeatherFetcher;
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

    private final WeatherFetcherProvider weatherFetcherProvider;

    @Override
    protected void doHandle(ForecastRequest forecastRequest) throws Exception {
        WeatherFetcher weatherFetcher = weatherFetcherProvider.getWeatherFetcher(forecastRequest.getModel());
        if (weatherFetcher == null){
            log.error("Model was not found with name: {}", forecastRequest.getModel());
            throw new IOException("Model not found with name " + forecastRequest.getModel());
        }
        WeatherData weatherData = weatherFetcher.fetch(forecastRequest.getTargetMunicipality(), forecastRequest.getCentroidMunicipality());
        forecastRequest.setWeatherData(weatherData);
    }
}
