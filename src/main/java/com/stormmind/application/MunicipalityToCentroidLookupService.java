package com.stormmind.application;

import org.springframework.stereotype.Component;

@Component
public interface MunicipalityToCentroidLookupService {

    String getCentroidMunicipality(String municipality);

}
