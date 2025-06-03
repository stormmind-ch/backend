package com.stormmind.presentation.dtos.intern;

import com.stormmind.domain.WeatherValue;

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
        WeatherValue weatherValue
) {
}
