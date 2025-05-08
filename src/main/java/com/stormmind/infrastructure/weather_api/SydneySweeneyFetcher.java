package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;

import java.net.URL;
import java.util.List;

/**
 * returns the previous 4 and coming week.
 */
public class SydneySweeneyFetcher extends AbstractOpenMeteoFetcher {

    public SydneySweeneyFetcher() {}

    public WeatherDataDTO sydneySweeneyFetch(Municipality targetMunicipal, Municipality centroidMunicipal) {

        WeatherDataDTO weatherDataDTO = new FNNFetcher().vanillaFetch(targetMunicipal, centroidMunicipal);

        /**
         * weatherData for previous 4 weeks [w-4,w-3,w-2,w-1]
         */
        for(int i = 1; i <= 4; i++){
            URL url = buildUrl(centroidMunicipal,i);
            WeatherValueDTO weatherValueDTO = this.fetchData(url);
            weatherDataDTO.archive().addFirst(weatherValueDTO);
        }

        return weatherDataDTO;
    }

}
