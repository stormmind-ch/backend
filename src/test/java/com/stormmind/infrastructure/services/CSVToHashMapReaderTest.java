package com.stormmind.infrastructure.services;

import com.stormmind.domain.Coordinates;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CSVToHashMapReaderTest {

    @Test
    void testReadCSVToMap() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, """
                name,lat,lon
                Zurich,47.3769,8.5417
                Bern,46.9481,7.4474
                """.getBytes());

        CSVToHashMapReader<String, Coordinates> reader = new CSVToHashMapReader<>(
                parts -> parts[0],
                parts -> new Coordinates(
                        Float.parseFloat(parts[1]),
                        Float.parseFloat(parts[2])
                )
        );

        Map<String, Coordinates> result = reader.read(tempFile.toString(),3);

        assertEquals(2, result.size());

        Coordinates zurich = result.get("Zurich");
        assertNotNull(zurich);
        assertEquals(47.3769f, zurich.getLatitude(), 0.0001);
        assertEquals(8.5417f, zurich.getLongitude(), 0.0001);

        Coordinates bern = result.get("Bern");
        assertNotNull(bern);
        assertEquals(46.9481f, bern.getLatitude(), 0.0001);
        assertEquals(7.4474f, bern.getLongitude(), 0.0001);

        Files.deleteIfExists(tempFile);
    }
}