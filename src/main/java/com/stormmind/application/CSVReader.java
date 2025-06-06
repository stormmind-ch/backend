package com.stormmind.application;

import java.io.IOException;

public interface CSVReader<T>{
    T read(String csvFile) throws IOException;
    T read(String csvFile, int columns) throws IOException;
    T read(String csvFile, int columns, int skipFirstNLines) throws IOException;
}
