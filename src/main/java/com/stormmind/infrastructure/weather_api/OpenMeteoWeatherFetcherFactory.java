package com.stormmind.infrastructure.weather_api;

import com.stormmind.application.weather.WeatherFetcher;
import com.stormmind.application.weather.WeatherFetcherProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenMeteoWeatherFetcherFactory implements WeatherFetcherProvider {
    private final Map<String, WeatherFetcher>weatherFetcherMap;

    @Override
    public WeatherFetcher getWeatherFetcher(String model){
        return weatherFetcherMap.get(model);
    }
}
