package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service(OpenMeteoWeatherWeatherFetcherCurrentWeek.BEAN_ID)
public class OpenMeteoWeatherWeatherFetcherCurrentWeek extends AbstractOpenMeteoWeatherFetcher {

    public static final String BEAN_ID = "FNN";

    @Override
    public WeatherDataDTO fetch(Municipality targetMunicipality, Municipality centroidMunicipality) {
        URL url = buildUrl(centroidMunicipality, 0);
        return new WeatherDataDTO(
                targetMunicipality.getName(),
                centroidMunicipality.getName(),
                new ArrayList<>(List.of(this.fetchData(url))),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
