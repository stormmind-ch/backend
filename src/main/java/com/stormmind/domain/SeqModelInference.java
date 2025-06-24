package com.stormmind.domain;

public record SeqModelInference(float temperature_mean, float sun_mean, float rain_sum,
                                float temperature_mean_1, float sun_mean_1, float rain_sum_1,
                                float temperature_mean_2, float sun_mean_2, float rain_sum_2
                                ) implements Inference {
}
