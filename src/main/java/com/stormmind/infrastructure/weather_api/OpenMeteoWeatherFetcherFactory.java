package com.stormmind.infrastructure.weather_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenMeteoWeatherFetcherFactory {
    private final Map<String, WeatherFetcher>weatherFetcherMap;

    public WeatherFetcher getWeatherFetcher(String model){
        return weatherFetcherMap.get(model);
    }
}
