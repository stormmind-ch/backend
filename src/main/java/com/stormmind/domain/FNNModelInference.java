package com.stormmind.domain;


public record FNNModelInference(float temperature_mean, float sun_mean, float rain_sum) implements Inference {
}
