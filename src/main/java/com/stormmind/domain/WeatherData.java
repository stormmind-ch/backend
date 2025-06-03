package com.stormmind.domain;

import com.stormmind.presentation.dtos.intern.WeatherValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class WeatherData {
    private String municipal;
    private String centroidMunicipal;
    private ArrayList<WeatherValue> forecast;
    private ArrayList<WeatherValue> archive;
    private ArrayList<WeatherValue> history;
}
