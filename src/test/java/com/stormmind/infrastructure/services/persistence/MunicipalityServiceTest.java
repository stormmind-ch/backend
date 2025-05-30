package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.repositories.MunicipalityRepository;
import com.stormmind.domain.Municipality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MunicipalityServiceTest {

    private MunicipalityRepository municipalityRepository;
    private MunicipalityService municipalityService;

    @BeforeEach
    void setUp() {
        municipalityRepository = mock(MunicipalityRepository.class);
        municipalityService = new MunicipalityService(municipalityRepository);
    }

    @Test
    void testGetAllMunicipalities() {
        List<Municipality> mockList = Arrays.asList(new Municipality(), new Municipality());
        when(municipalityRepository.findAll()).thenReturn(mockList);

        List<Municipality> result = municipalityService.getAllMunicipalities();

        assertEquals(2, result.size());
        verify(municipalityRepository).findAll();
    }

    @Test
    void testGetMunicipalityById_found() {
        Municipality municipality = new Municipality();
        when(municipalityRepository.getMunicipalitiesByName("Zürich")).thenReturn(Optional.of(municipality));

        Municipality result = municipalityService.getMunicipalityById("Zürich");

        assertNotNull(result);
        verify(municipalityRepository).getMunicipalitiesByName("Zürich");
    }

    @Test
    void testGetMunicipalityById_notFound() {
        when(municipalityRepository.getMunicipalitiesByName("Bern")).thenReturn(Optional.empty());

        Municipality result = municipalityService.getMunicipalityById("Bern");

        assertNull(result);
        verify(municipalityRepository).getMunicipalitiesByName("Bern");
    }
}