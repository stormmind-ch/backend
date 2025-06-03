package com.stormmind.infrastructure.services.persistence;

import com.stormmind.infrastructure.persistence.MunicipalityToClusterPersistenceAdapter;
import com.stormmind.infrastructure.repositories.MunicipalityToClusterRepository;
import com.stormmind.domain.MunicipalityToCluster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MunicipalityToClusterServiceTest {

    private MunicipalityToClusterRepository municipalityToClusterRepository;
    private MunicipalityToClusterPersistenceAdapter municipalityToClusterPersistenceAdapter;

    @BeforeEach
    void setUp() {
        municipalityToClusterRepository = mock(MunicipalityToClusterRepository.class);
        municipalityToClusterPersistenceAdapter = new MunicipalityToClusterPersistenceAdapter(municipalityToClusterRepository);
    }

    @Test
    void testGetAllMunicipalitiesToCluster() {
        List<MunicipalityToCluster> mockList = Arrays.asList(new MunicipalityToCluster(), new MunicipalityToCluster());
        when(municipalityToClusterRepository.findAll()).thenReturn(mockList);

        List<MunicipalityToCluster> result = municipalityToClusterPersistenceAdapter.getAllMunicipalityToCluster();

        assertEquals(2, result.size());
        verify(municipalityToClusterRepository).findAll();
    }

    @Test
    void testGetMunicipalityToClusterByMunicipality() {
        String municipalityName = "Winterthur";
        MunicipalityToCluster mockEntity = new MunicipalityToCluster();

        when(municipalityToClusterRepository.getMunicipalityToCluster6ByMunicipality(municipalityName))
                .thenReturn(mockEntity);

        MunicipalityToCluster result = municipalityToClusterPersistenceAdapter.findByMunicipality(municipalityName);

        assertNotNull(result);
        verify(municipalityToClusterRepository).getMunicipalityToCluster6ByMunicipality(municipalityName);
    }
}