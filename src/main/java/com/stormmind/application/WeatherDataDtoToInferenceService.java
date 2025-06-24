package com.stormmind.application;

import com.stormmind.domain.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class WeatherDataDtoToInferenceService {

    public static Inference weatherDataDTOToInference(WeatherData dto, String modelType) {
        List<WeatherValue> forecast = dto.getForecast();

        if (Objects.equals(modelType, "FNN")) {
            return buildFNNInference(forecast);
        } else if (Objects.equals(modelType, "LSTM") || Objects.equals(modelType, "TRANSFORMER")) {
            return buildSeqInference(dto);
        } else {
            throw new IllegalArgumentException("Unsupported model type: " + modelType);
        }
    }

    private static Inference buildFNNInference(List<WeatherValue> forecast) {
        DoubleStream temperatureStream = forecast.stream()
                .flatMapToDouble(w -> w.getTemperature().stream().mapToDouble(Double::doubleValue));
        DoubleStream sunshineStream = forecast.stream()
                .flatMapToDouble(w -> w.getSunshine().stream().mapToDouble(Double::doubleValue));
        DoubleStream rainStream = forecast.stream()
                .flatMapToDouble(w -> w.getRain().stream().mapToDouble(Double::doubleValue));

        float tempMean = (float) temperatureStream.average().orElse(0.0);
        float sunMean = (float) sunshineStream.average().orElse(0.0);
        float rainSum = (float) rainStream.sum();

        return new FNNModelInference(tempMean, sunMean, rainSum);
    }

    private static Inference buildSeqInference(WeatherData dto) {
        List<WeatherValue> archive = dto.getArchive();
        List<WeatherValue> forecast = dto.getForecast();

        if (archive.size() < 2 || forecast.size() < 1) {
            throw new IllegalArgumentException("Expected at least 2 archive and 1 forecast values for sequence model");
        }

        WeatherValue t0 = archive.get(0);      // oldest
        WeatherValue t1 = archive.get(1);      // more recent
        WeatherValue t2 = forecast.get(0);     // future

        float[] f0 = extractFeatures(t0);
        float[] f1 = extractFeatures(t1);
        float[] f2 = extractFeatures(t2);

        return new SeqModelInference(
                f0[0], f0[1], f0[2],
                f1[0], f1[1], f1[2],
                f2[0], f2[1], f2[2]
        );
    }


    private static float[] extractFeatures(WeatherValue wv) {
        float tempMean = (float) wv.getTemperature().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        float sunMean  = (float) wv.getSunshine().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        float rainSum  = (float) wv.getRain().stream().mapToDouble(Double::doubleValue).sum();
        return new float[]{tempMean, sunMean, rainSum};
    }
}

