package com.stormmind.infrastructure.services;

import com.stormmind.application.ModelToNrClustersLookupService;
import com.stormmind.domain.Coordinates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

@Repository
public class ModelToNRClustersFileLookupService implements ModelToNrClustersLookupService{


    private final Map<String, Integer> lookup;
    Function<String[], String> keyMapper = parts -> parts[0].trim();
    Function<String[], Integer> valueMapper = parts ->
            Integer.parseInt(parts[1].trim());

    public ModelToNRClustersFileLookupService(@Value("${model.nrclusters.csv.path}")String csvFile) throws IOException {
        this.lookup = createLookup(csvFile);
    }

    @Override
    public int getNrOfClusters(String model) {
        return lookup.get(model);
    }

    public Map<String, Integer> createLookup(String csvFile) throws IOException {
        CSVToHashMapReader<String, Integer> reader =
                new CSVToHashMapReader<>(keyMapper, valueMapper);

        return reader.read(csvFile, 2);
    }
}
