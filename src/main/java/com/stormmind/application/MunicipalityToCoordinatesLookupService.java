package com.stormmind.application;

import com.stormmind.domain.Coordinates;
import org.springframework.stereotype.Component;

@Component
public interface MunicipalityToCoordinatesLookupService extends LookupService{
    Coordinates getCoordinatesForMunicipality(String municipality);
}
