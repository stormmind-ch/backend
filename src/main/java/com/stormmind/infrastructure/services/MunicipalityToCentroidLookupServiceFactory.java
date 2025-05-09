package com.stormmind.infrastructure.services;
import com.stormmind.application.MunicipalityToCentroidLookupService;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityToCentroidLookupServiceFactory {

    public MunicipalityToCentroidLookupService fromFile(String filePath) {
        return new MunicipalityToCentroidFileLookupService(filePath);
    }
}
