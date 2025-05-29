package com.stormmind.presentation.dtos.response.forecast;

import com.stormmind.domain.Municipality;

public record ForecastDto(Municipality municipality, float forecast) {
}
