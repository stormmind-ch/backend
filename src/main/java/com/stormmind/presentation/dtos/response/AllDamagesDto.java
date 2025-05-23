package com.stormmind.presentation.dtos.response;

import com.stormmind.domain.Damage;

import java.util.List;

public record AllDamagesDto(List<Damage> damages) {}
