package com.stormmind.infrastructure.services;

import com.stormmind.application.MunicipalityToCentroidLookupService;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.function.Function;

/**
 * This class is instantiated through its factory: MunicipalityToCentroidLookupServiceFactory
 */
public class MunicipalityToCentroidFileLookupService implements MunicipalityToCentroidLookupService {

    private final Map<String, String> lookup;
    Function<String[], String> keyMapper = parts -> parts[0].trim();
    Function<String[], String> valueMapper = parts ->
            parts[1].trim();

    public MunicipalityToCentroidFileLookupService(String csvFile) {
        this.lookup = createLookup(csvFile);
    }


    @Override
    public String getCentroidMunicipality(String municipality) {
        return lookup.get(municipality);
    }

    private Map<String, String> createLookup(String csvFile){
        CSVToHashMapReader<String, String> reader =
                new CSVToHashMapReader<>(keyMapper, valueMapper);
        return reader.read(csvFile, 2, 1);
    }
}
