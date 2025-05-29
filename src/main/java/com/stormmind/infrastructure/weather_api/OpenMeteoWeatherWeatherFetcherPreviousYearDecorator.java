package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * returns the following weeks.
 * All numbers are in weeks and relative to this week.
 *  -4,  -3,  -2,  -1,  -0
 * -56, -55, -54, -53, -52
 */
@Service(OpenMeteoWeatherWeatherFetcherPreviousYearDecorator.BEAN_ID)
public class OpenMeteoWeatherWeatherFetcherPreviousYearDecorator extends AbstractOpenMeteoWeatherFetcher {

    // TODO CHANGE TO CORRECT MODEL
    public static final String BEAN_ID = "SEQ2SEQ";
    private final OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator previousYearWeatherFetcher;

    public OpenMeteoWeatherWeatherFetcherPreviousYearDecorator(OpenMeteoWeatherWeatherFetcherPreviousMonthDecorator openMeteoWeatherFetcherPreviousMonthDecorator) {
        this.previousYearWeatherFetcher = openMeteoWeatherFetcherPreviousMonthDecorator;
    }
    @Cacheable(cacheNames = "weather-by-cluster", key = "#centroidMunicipality.name")
    public WeatherDataDTO fetch(Municipality targetMunicipality, Municipality centroidMunicipality) {
        WeatherDataDTO weatherDataDTO = previousYearWeatherFetcher.fetch(targetMunicipality, centroidMunicipality);
         //weatherData for the -5x weeks.
        for(int i = 4; i >= 0; i--){
            URL url = buildUrl(centroidMunicipality,-(52+i));
            WeatherValueDTO weatherValueDTO = this.fetchData(url);
            weatherDataDTO.history().addFirst(weatherValueDTO);
        }
        return weatherDataDTO;
    }
}
