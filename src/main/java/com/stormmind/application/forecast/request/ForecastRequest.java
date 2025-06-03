package com.stormmind.application.forecast.request;

import com.stormmind.domain.Forecast;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster;
import com.stormmind.domain.WeatherData;
import com.stormmind.application.weather.WeatherFetcher;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForecastRequest implements Request {

    private String model;
    private String queriedMunicipality;
    private MunicipalityToCluster mapping;
    private Municipality targetMunicipality;
    private Municipality centroidMunicipality;
    private WeatherFetcher weatherFetcher;
    private WeatherData weatherData;
    private Forecast forecast;
}
