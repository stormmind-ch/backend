package com.stormmind.presentation;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.infrastructure.services.ForecastService;
import com.stormmind.presentation.dtos.response.HeatMapDataDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/DummyHeatMapData")
    private List<HeatMapDataDTO> getHeatMapData() {
        return Arrays.asList(
                new HeatMapDataDTO(47.139085, 6.9665391, 100, "Sonvilier", "1972-03-09"),
                new HeatMapDataDTO(46.374412, 7.0729789, 100, "Ormont-Dessous", "1972-04-08"),
                new HeatMapDataDTO(47.12861, 8.7500299, 100, "Einsiedeln", "1972-05-01"),
                new HeatMapDataDTO(46.186708, 7.3051008,  100, "Nendaz", "1972-06-16"),
                new HeatMapDataDTO(46.737906, 8.2052569, 100, "Hasliberg", "1972-07-01"),
                new HeatMapDataDTO(46.394285, 7.7591799, 100, "Ferden", "1972-07-16"),
                new HeatMapDataDTO(46.875225, 7.7974921,  100, "Eggiwil", "1972-07-18"));
    }
}