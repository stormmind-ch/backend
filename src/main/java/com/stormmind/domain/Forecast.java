package com.stormmind.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Forecast {
    private float forecast;
    private Municipality centroid;
    private Municipality municipality;
}


