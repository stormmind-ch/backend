package com.stormmind.presentation;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.stormmind.application.ForecastService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {
        "https://stormmind.ch",
        "https://www.stormmind.ch"
})
@RequestMapping("/api/forecast")
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