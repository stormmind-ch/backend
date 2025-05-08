package com.stormmind.application;

import com.stormmind.domain.Municipality;
import org.springframework.stereotype.Component;

@Component
public interface MunicipalityToCentroidLookupService {

    String getCentroidMunicipality(String municipality);

}
