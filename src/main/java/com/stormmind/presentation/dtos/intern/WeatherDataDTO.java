package com.stormmind.presentation.dtos.intern;

import java.util.List;

/**
 *
 * @param municipal - Selected by the User.
 * @param centroidMunicipal - Used as input for the model.
 * @param forecast - The weather data for today until today + 7 days.
 * @param archive - Depending on the model, the last, up until the last 4 weeks.
 * @param history - Depending on the model, empty or the weeks today - (52 + 4 weeks) until today - (52 - 1 week)
 *
 */

public record WeatherDataDTO(
        String municipal,
        String centroidMunicipal,
        List<WeatherValueDTO> forecast,
        List<WeatherValueDTO> archive,
        List<WeatherValueDTO> history
) {}

