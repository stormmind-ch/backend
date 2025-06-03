package com.stormmind.application.weather;

import com.stormmind.domain.Municipality;
import com.stormmind.domain.WeatherData;

public interface WeatherFetcher {
    WeatherData fetch(Municipality targetMunicipality, Municipality centroidMunicipality);
}
