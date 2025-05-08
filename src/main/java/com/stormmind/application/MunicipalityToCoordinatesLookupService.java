package com.stormmind.application;

import com.stormmind.domain.Coordinates;
import org.springframework.stereotype.Component;

@Component
public interface MunicipalityToCoordinatesLookupService {
    Coordinates getCoordinatesForMunicipality(String municipality);
}
