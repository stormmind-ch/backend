package com.stormmind.infrastructure.persistence;

import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Municipality;
import com.stormmind.infrastructure.repositories.MunicipalityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MunicipalityPersistenceAdapterTest {

    private MunicipalityRepository municipalityRepository;
    private MunicipalityPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        municipalityRepository = mock(MunicipalityRepository.class);
        adapter = new MunicipalityPersistenceAdapter(municipalityRepository);
    }

    @Test
    void testFindAll_returnsAllMunicipalities() {
        // Arrange
        List<Municipality> mockList = List.of(
                new Municipality("A", new Coordinates(1, 2)),
                new Municipality("B", new Coordinates(3, 4))
        );
        when(municipalityRepository.findAll()).thenReturn(mockList);

        // Act
        List<Municipality> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(municipalityRepository).findAll();
    }

    @Test
    void testFindByName_found() {
        // Arrange
        String name = "TestTown";
        Municipality expected = new Municipality(name, new Coordinates(5, 6));
        when(municipalityRepository.findById(name)).thenReturn(Optional.of(expected));

        // Act
        Municipality result = adapter.findByName(name);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(municipalityRepository).findById(name);
    }

    @Test
    void testFindByName_notFound_returnsNull() {
        // Arrange
        String name = "UnknownTown";
        when(municipalityRepository.findById(name)).thenReturn(Optional.empty());

        // Act
        Municipality result = adapter.findByName(name);

        // Assert
        assertNull(result);
        verify(municipalityRepository).findById(name);
    }
}