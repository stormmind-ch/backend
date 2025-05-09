package com.stormmind.infrastructure.services;

import com.stormmind.application.ModelToClustersLookupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

@Repository
public class ModelToClusterFileLookupService implements ModelToClustersLookupService {


    private final Map<String, String> lookup;
    Function<String[], String> keyMapper = parts -> parts[0].trim();
    Function<String[], String> valueMapper = parts ->
            parts[1].trim();

    public ModelToClusterFileLookupService(@Value("${model.nrclusters.csv.path}")String csvFile) throws IOException {
        this.lookup = createLookup(csvFile);
    }

    @Override
    public String getClusterFile(String model) {
        return lookup.get(model);
    }

    public Map<String, String> createLookup(String csvFile) {
        CSVToHashMapReader<String, String> reader =
                new CSVToHashMapReader<>(keyMapper, valueMapper);

        return reader.read(csvFile, 2);
    }
}
