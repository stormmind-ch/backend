package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

/**
 * returns the following weeks.
 * All numbers are in weeks and relative to this week.
 *  -4,  -3,  -2,  -1,  -0
 * -56, -55, -54, -53, -52
 */
@Component
public class AnaDeArmasFetcher extends AbstractOpenMeteoFetcher {

    private SydneySweeneyFetcher sydney;

    public AnaDeArmasFetcher(SydneySweeneyFetcher sydneySweeneyFetcher) {
        this.sydney = sydneySweeneyFetcher;
    }

    public WeatherDataDTO anaDeArmasFetch(Municipality targetMunicipal, Municipality centroidMunicipal) {

        WeatherDataDTO weatherDataDTO = sydney.sydneySweeneyFetch(targetMunicipal, centroidMunicipal);

        /**
         * weatherData for the -5x weeks.
         */
        for(int i = 4; i >= 0; i--){
            URL url = buildUrl(centroidMunicipal,-(52+i));
            WeatherValueDTO weatherValueDTO = this.fetchData(url);
            weatherDataDTO.history().addFirst(weatherValueDTO);
        }

        return weatherDataDTO;
    }

}
