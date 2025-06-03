package com.stormmind.infrastructure.services.persistence;

import com.stormmind.domain.Municipality;
import com.stormmind.infrastructure.persistence.MunicipalityPersistenceAdapter;
import com.stormmind.infrastructure.repositories.MunicipalityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MunicipalityServiceTest {

    private MunicipalityRepository municipalityRepository;
    private MunicipalityPersistenceAdapter municipalityPersistenceAdapter;

    @BeforeEach
    void setUp() {
        municipalityRepository = mock(MunicipalityRepository.class);
        municipalityPersistenceAdapter = new MunicipalityPersistenceAdapter(municipalityRepository);
    }

    @Test
    void testGetAllMunicipalities() {
        List<Municipality> mockList = Arrays.asList(new Municipality(), new Municipality());
        when(municipalityRepository.findAll()).thenReturn(mockList);

        List<Municipality> result = municipalityPersistenceAdapter.findAll();

        assertEquals(2, result.size());
        verify(municipalityRepository).findAll();
    }

    @Test
    void testGetMunicipalityByName_found() {
        Municipality municipality = new Municipality();
        when(municipalityRepository.findById("Zürich")).thenReturn(Optional.of(municipality));

        Municipality result = municipalityPersistenceAdapter.findByName("Zürich");

        assertNotNull(result);
        verify(municipalityRepository).findById("Zürich");
    }

    @Test
    void testGetMunicipalityByName_notFound() {
        when(municipalityRepository.findById("Bern")).thenReturn(Optional.empty());

        Municipality result = municipalityPersistenceAdapter.findByName("Bern");

        assertNull(result);
        verify(municipalityRepository).findById("Bern");
    }
}