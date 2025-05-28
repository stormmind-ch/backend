package com.stormmind.presentation;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.application.ForecastService;
import com.stormmind.presentation.dtos.response.forecast.ForecastDto;
import com.stormmind.presentation.dtos.response.forecast.ForecastForAllMunicipalitiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    private ForecastDto getForecastForExplicitMunicipality(@PathVariable String model, @PathVariable String municipality) throws ModelNotFoundException, TranslateException, MalformedModelException, IOException {
        log.info("Got request for forecast one municipality, with PathVariables: {} {}", model, municipality);
        return forecastService.getForecast(model, municipality);
    }

    @GetMapping("/allmunicipalities/{model}")
    private ForecastForAllMunicipalitiesDto getForecastForAllMunicipalities(@PathVariable String model) throws ModelNotFoundException, TranslateException, MalformedModelException, IOException{
        log.info("Got request for all municipalities, with PathVariable: {}", model);
        return forecastService.getForecastForAllMunicipalities(model);
    }


}