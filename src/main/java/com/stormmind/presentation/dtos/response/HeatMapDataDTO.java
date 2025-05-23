package com.stormmind.presentation.dtos.response;

public record HeatMapDataDTO(
        Double latitude,
        Double longitude,
        int weight,
        String municipal,
        String date
) {}
