package com.stormmind.infrastructure.weather_api;

import com.stormmind.domain.Municipality;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
public class FNNFetcher extends AbstractOpenMeteoFetcher{

    public FNNFetcher() {}

    public WeatherDataDTO vanillaFetch(Municipality targetMunicipal, Municipality centroidMunicipal) {
        URL url = buildUrl(centroidMunicipal, 0);
        return new WeatherDataDTO(
                targetMunicipal.name(),
                centroidMunicipal.name(),
                List.of(this.fetchData(url)),
                List.of(),
                List.of()
        );
    }
}
