package com.stormmind.infrastructure.services;

import com.stormmind.application.MunicipalityToCoordinatesLookupService;
import com.stormmind.domain.Coordinates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

@Repository
public class MunicipalityToCoordinatesFileLookupLookupService implements MunicipalityToCoordinatesLookupService {

    private final Map<String, Coordinates> lookup;

    Function<String[], String> keyMapper = parts -> parts[0].trim();
    Function<String[], Coordinates> valueMapper = parts -> new Coordinates(
            Float.parseFloat(parts[1].trim()),
            Float.parseFloat(parts[2].trim())
    );

    public MunicipalityToCoordinatesFileLookupLookupService(@Value("${municipality.csv.path}") String csvFile){
        lookup = createLookup(csvFile);
    }

    @Override
    public Coordinates getCoordinatesForMunicipality(String municipality) {
        return lookup.get(municipality);
    }

    public Map<String, Coordinates> createLookup(String csvFile) {
        CSVToHashMapReader<String, Coordinates> reader =
                new CSVToHashMapReader<>(keyMapper, valueMapper);

        return reader.read(csvFile, 3, 1);
    }


}