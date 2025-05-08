package com.stormmind.domain;


public record FNNModelPrompt (float temperature_mean, float sun_mean, float rain_sum) implements AIPrompt{
}
