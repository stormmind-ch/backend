package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.domain.WeatherData;
import com.stormmind.domain.WeatherValue;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * returns the previous 4 and coming week.
 */
@Service(OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator.BEAN_ID)
public class OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator extends AbstractOpenMeteoWeatherFetcher {

    //TODO CHANGE TO CORRECT MODEL
    public static final String BEAN_ID = "TRANSFORMER";
    private final OpenMeteoWeatherWeatherFetcherCurrentWeek openMeteoWeatherFetcher;

    public OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator(OpenMeteoWeatherWeatherFetcherCurrentWeek openMeteoWeatherFetcher) {
        this.openMeteoWeatherFetcher = openMeteoWeatherFetcher;
    }
    @Cacheable(cacheNames = "weather-by-cluster", key = "#centroidMunicipality.name")
    public WeatherData fetch(Municipality targetMunicipality, Municipality centroidMunicipality) {

        WeatherData weatherData = openMeteoWeatherFetcher.fetch(targetMunicipality, centroidMunicipality);
        // weatherData for previous 4 weeks [w-4,w-3,w-2,w-1]
        for(int i = 4; i >= 1; i--){
            URL url = buildUrl(centroidMunicipality,i);
            WeatherValue weatherValue = this.fetchData(url);
            weatherData.getArchive().addFirst(weatherValue);
        }

        return weatherData;
    }

}
