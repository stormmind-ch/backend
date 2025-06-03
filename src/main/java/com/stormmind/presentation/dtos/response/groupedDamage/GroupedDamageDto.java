package com.stormmind.presentation.dtos.response.groupedDamage;

import com.stormmind.domain.Coordinates;
import com.stormmind.domain.GroupedDamage;
import com.stormmind.domain.Municipality;

public record GroupedDamageDto(Municipality municipality, int groupedDamages) {
    /** Convenience ctor that converts a domain object into the DTO */
    public GroupedDamageDto(GroupedDamage groupedDamage) {
        this(
                new Municipality(
                        groupedDamage.getMunicipality(),
                        new Coordinates(
                                groupedDamage.getLatitude(),
                                groupedDamage.getLongitude())
                ),
                (int) groupedDamage.getOccurrenceCount()
        );
    }
}
