package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.repositories.MunicipalityToClusterRepository;
import com.stormmind.domain.MunicipalityToCluster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MunicipalityToClusterServiceTest {

    private MunicipalityToClusterRepository municipalityToClusterRepository;
    private MunicipalityToClusterService municipalityToClusterService;

    @BeforeEach
    void setUp() {
        municipalityToClusterRepository = mock(MunicipalityToClusterRepository.class);
        municipalityToClusterService = new MunicipalityToClusterService(municipalityToClusterRepository);
    }

    @Test
    void testGetAllMunicipalitiesToCluster() {
        List<MunicipalityToCluster> mockList = Arrays.asList(new MunicipalityToCluster(), new MunicipalityToCluster());
        when(municipalityToClusterRepository.findAll()).thenReturn(mockList);

        List<MunicipalityToCluster> result = municipalityToClusterService.getAllMunicipalitiesToCluster();

        assertEquals(2, result.size());
        verify(municipalityToClusterRepository).findAll();
    }

    @Test
    void testGetMunicipalityToClusterByMunicipality() {
        String municipalityName = "Winterthur";
        MunicipalityToCluster mockEntity = new MunicipalityToCluster();

        when(municipalityToClusterRepository.getMunicipalityToCluster6ByMunicipality(municipalityName))
                .thenReturn(mockEntity);

        MunicipalityToCluster result = municipalityToClusterService.getMunicipalityToClusterByMunicipality(municipalityName);

        assertNotNull(result);
        verify(municipalityToClusterRepository).getMunicipalityToCluster6ByMunicipality(municipalityName);
    }
}