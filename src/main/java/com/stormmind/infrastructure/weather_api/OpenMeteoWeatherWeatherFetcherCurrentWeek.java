package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.domain.WeatherData;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Primary
@Service(OpenMeteoWeatherWeatherFetcherCurrentWeek.BEAN_ID)
public class OpenMeteoWeatherWeatherFetcherCurrentWeek extends AbstractOpenMeteoWeatherFetcher {

    public static final String BEAN_ID = "FNN";

    @Override
    @Cacheable(cacheNames = "weather-by-cluster", key = "#centroidMunicipality.name")
    public WeatherData fetch(Municipality targetMunicipality, Municipality centroidMunicipality) {
        URL url = buildUrl(centroidMunicipality, 0);
        return new WeatherData(
                targetMunicipality.getName(),
                centroidMunicipality.getName(),
                new ArrayList<>(List.of(this.fetchData(url))),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
