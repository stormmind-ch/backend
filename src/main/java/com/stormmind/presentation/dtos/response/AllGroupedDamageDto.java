package com.stormmind.presentation.dtos.response;

import com.stormmind.domain.GroupedDamage;

import java.util.List;

public record AllGroupedDamageDto(List<GroupedDamage> AllGroupedDamages) {
}
