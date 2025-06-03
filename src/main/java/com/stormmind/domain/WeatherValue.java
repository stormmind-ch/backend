package com.stormmind.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WeatherValue {
    private List<Double> temperature;
    private List<Double> sunshine;
    private List<Double> rain;
    private List<Double> sno;
}
