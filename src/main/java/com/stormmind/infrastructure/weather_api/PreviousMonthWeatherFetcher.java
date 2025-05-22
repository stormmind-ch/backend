package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * returns the previous 4 and coming week.
 */
@Component
public class PreviousMonthWeatherFetcher extends AbstractOpenMeteoFetcher {

    private FNNFetcher fnnFetcher;

    public PreviousMonthWeatherFetcher(FNNFetcher fnnFetcher) {
        this.fnnFetcher = fnnFetcher;
    }

    public WeatherDataDTO fetch(Municipality targetMunicipal, Municipality centroidMunicipal) {

        WeatherDataDTO weatherDataDTO = fnnFetcher.vanillaFetch(targetMunicipal, centroidMunicipal);

        /**
         * weatherData for previous 4 weeks [w-4,w-3,w-2,w-1]
         */
        for(int i = 4; i >= 1; i--){
            URL url = buildUrl(centroidMunicipal,i);
            WeatherValueDTO weatherValueDTO = this.fetchData(url);
            weatherDataDTO.archive().addFirst(weatherValueDTO);
        }

        return weatherDataDTO;
    }

}
