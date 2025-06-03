package com.stormmind.application.weather;

public interface WeatherFetcherProvider {
    WeatherFetcher getWeatherFetcher(String model);
}
