package com.stormmind.infrastructure.services;

import com.stormmind.application.CSVReader;
import com.stormmind.domain.Coordinates;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CSV Reader which reads a .csv file into a Hashmap.
 * @param <K>
 * @param <V>
 */
public class CSVToHashMapReader<K, V> implements CSVReader<Map<K, V>> {

    private final Function<String[], K> keyMapper;
    private final Function<String[], V> valueMapper;


    /***
     * @param keyMapper decides how the keys should be creatd
     * @param valueMapper decides how the values should be created.
     */
    public CSVToHashMapReader(Function<String[], K> keyMapper, Function<String[], V> valueMapper) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }


    /**
     * Default implementation and expects the csv file to have two columns. <K,V>
     * @param csvFile
     * @return
     * @throws IOException
     */
    @Override
    public Map<K, V> read(String csvFile) throws IOException {
        return read(csvFile, 2, 0);
    }

    @Override
    public Map<K, V> read(String csvFile, int columns) throws IOException {
        return read(csvFile, columns, 0);
    }

    @Override
    public Map<K, V> read(String csvFile, int columns, int skipFirstNLines) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(csvFile))) {
            return lines
                    .skip(skipFirstNLines)
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == columns)
                    .collect(Collectors.toMap(
                            keyMapper,
                            valueMapper
                    ));
        }
    }
}
