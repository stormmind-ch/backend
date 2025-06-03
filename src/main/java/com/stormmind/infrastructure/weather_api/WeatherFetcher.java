package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.domain.WeatherData;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;

public interface WeatherFetcher {
    WeatherData fetch(Municipality targetMunicipality, Municipality centroidMunicipality);
}
