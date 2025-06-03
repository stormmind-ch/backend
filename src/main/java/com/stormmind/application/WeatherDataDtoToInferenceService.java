package com.stormmind.application;

import com.stormmind.domain.FNNModelInference;
import com.stormmind.domain.Inference;
import com.stormmind.domain.WeatherData;
import com.stormmind.domain.WeatherValue;
import com.stormmind.presentation.dtos.intern.WeatherDataDTO;
import com.stormmind.presentation.dtos.intern.WeatherValueDTO;

import java.util.List;
import java.util.stream.DoubleStream;

public class WeatherDataDtoToInferenceService {
    public static Inference weatherDataDTOToInference(WeatherData dto) {
        List<WeatherValue> forecast = dto.getForecast();

        DoubleStream temperatureStream = forecast.stream()
                .flatMapToDouble(w -> w.getTemperature().stream().mapToDouble(Double::doubleValue));
        DoubleStream sunshineStream = forecast.stream()
                .flatMapToDouble(w -> w.getSunshine().stream().mapToDouble(Double::doubleValue));
        DoubleStream rainStream = forecast.stream()
                .flatMapToDouble(w -> w.getRain().stream().mapToDouble(Double::doubleValue));

        double tempMean = temperatureStream.average().orElse(0.0);
        double sunMean = sunshineStream.average().orElse(0.0);
        double rainSum = rainStream.sum();

        return new FNNModelInference((float) tempMean, (float) sunMean, (float) rainSum);
    }
}
