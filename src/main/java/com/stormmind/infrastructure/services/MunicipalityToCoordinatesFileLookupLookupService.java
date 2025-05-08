package com.stormmind.infrastructure.services;

import com.stormmind.application.MunicipalityToCoordinatesLookupService;
import com.stormmind.domain.Coordinates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class MunicipalityToCoordinatesFileLookupLookupService implements MunicipalityToCoordinatesLookupService {

    private final HashMap<String, Coordinates> lookup;

    public MunicipalityToCoordinatesFileLookupLookupService(@Value("${municipality.csv.path}") String csvFile) throws IOException {
        lookup = createLookup(csvFile);
    }

    @Override
    public Coordinates getCoordinatesForMunicipality(String municipality) {
        return lookup.get(municipality);
    }

    private HashMap<String, Coordinates> createLookup(String csvFile) throws IOException{
        try (Stream<String> lines = Files.lines(Paths.get(csvFile))) {
            return (HashMap<String, Coordinates>) lines
                    .skip(1) // skip header
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == 3)
                    .collect(Collectors.toMap(
                            parts -> parts[0].trim(), // municipality
                            parts -> new Coordinates(
                                    Float.parseFloat(parts[1].trim()), // latitude
                                    Float.parseFloat(parts[2].trim())  // longitude
                            )
                    ));
        }
    }
}

