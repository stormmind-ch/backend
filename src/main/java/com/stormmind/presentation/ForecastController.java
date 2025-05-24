package com.stormmind.presentation;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.application.ForecastService;
import com.stormmind.presentation.dtos.response.HeatMapDataDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService){
        this.forecastService = forecastService;
    }

    @GetMapping("/{model}/{municipality}")
    private float getForecast(@PathVariable String model, @PathVariable String municipality) throws ModelNotFoundException, TranslateException, MalformedModelException, IOException {
        return forecastService.getForecast(model, municipality);
    }

}