package com.stormmind.presentation;

import com.stormmind.application.ForecastService;
import com.stormmind.domain.Forecast;
import com.stormmind.presentation.dtos.response.forecast.ForecastDto;
import com.stormmind.presentation.dtos.response.forecast.ForecastForAllMunicipalitiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {
        "https://stormmind.ch",
        "https://www.stormmind.ch"
})
@RequestMapping("/api/forecast")
@Slf4j
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService){
        this.forecastService = forecastService;
    }

    @GetMapping("/{model}/{municipality}")
    private ForecastDto getForecastForExplicitMunicipality(@PathVariable String model, @PathVariable String municipality) throws Exception {
        log.info("Got request for forecast one municipality, with PathVariables: {} {}", model, municipality);
        Forecast forecast = forecastService.getForecast(model, municipality);
        return new ForecastDto(forecast);
    }

    @GetMapping("/allmunicipalities/{model}")
    private ForecastForAllMunicipalitiesDto getForecastForAllMunicipalities(@PathVariable String model) throws Exception {
        log.info("Got request for all municipalities, with PathVariable: {}", model);
        List<Forecast> forecastList = forecastService.getForecastForAllMunicipalities(model);
        List<ForecastDto> forecastDtoList = forecastList.stream().map(ForecastDto::new).toList();
        return new ForecastForAllMunicipalitiesDto(forecastDtoList);
    }
}