package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.domain.WeatherData;
import com.stormmind.domain.WeatherValue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * returns the previous 2 and coming week.
 */
@Service(OpenMeteoWeatherWeatherFetcherPrevious2WeeksDecorator.BEAN_ID)
public class OpenMeteoWeatherWeatherFetcherPrevious2WeeksDecorator extends AbstractOpenMeteoWeatherFetcher {

    public static final String BEAN_ID = "SEQUENCE_MODELS";
    private final OpenMeteoWeatherWeatherFetcherCurrentWeek openMeteoWeatherFetcher;

    public OpenMeteoWeatherWeatherFetcherPrevious2WeeksDecorator(OpenMeteoWeatherWeatherFetcherCurrentWeek openMeteoWeatherFetcher) {
        this.openMeteoWeatherFetcher = openMeteoWeatherFetcher;
    }
    @Cacheable(cacheNames = "weather-by-cluster-2", key = "#centroidMunicipality.name")
    public WeatherData fetch(Municipality targetMunicipality, Municipality centroidMunicipality) {

        WeatherData weatherData = openMeteoWeatherFetcher.fetch(targetMunicipality, centroidMunicipality);
        // weatherData for previous 4 weeks [w-2,w-1]
        for(int i = 2; i >= 1; i--){
            URL url = buildUrl(centroidMunicipality,i);
            WeatherValue weatherValue = this.fetchData(url);
            weatherData.getArchive().addFirst(weatherValue);
        }

        return weatherData;
    }

}
