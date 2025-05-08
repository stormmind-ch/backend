package com.stormmind.presentation.dtos.intern;

import java.util.List;

/**
 *
 * @param temperature - Hourly data ( Due to the mean temp not being provided by the forecast api)
 * @param sunshine - Duration
 * @param rain - Daily sum
 * @param snow - Daily sum
 *
 * Consists of the Data of a complete week.
 */

public record WeatherValueDTO(
        List<Double> temperature,
        List<Double> sunshine,
        List<Double> rain,
        List<Double> snow
) {
}
