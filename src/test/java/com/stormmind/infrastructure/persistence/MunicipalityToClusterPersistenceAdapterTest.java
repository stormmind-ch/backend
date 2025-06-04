package com.stormmind.infrastructure.persistence;

import com.stormmind.domain.Coordinates;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster;
import com.stormmind.infrastructure.repositories.MunicipalityToClusterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MunicipalityToClusterPersistenceAdapterTest {

    private MunicipalityToClusterRepository repository;
    private MunicipalityToClusterPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(MunicipalityToClusterRepository.class);
        adapter = new MunicipalityToClusterPersistenceAdapter(repository);
    }

    @Test
    void testFindByMunicipality_returnsCorrectEntry() {
        // Arrange
        String municipalityName = "TestTown";
        Municipality municipality = new Municipality(municipalityName, new Coordinates(1.0f, 2.0f));
        MunicipalityToCluster expected = new MunicipalityToCluster(municipality.getName(), "cluster-1");

        when(repository.getMunicipalityToCluster6ByMunicipality(municipalityName)).thenReturn(expected);

        // Act
        MunicipalityToCluster result = adapter.findByMunicipality(municipalityName);

        // Assert
        assertNotNull(result);
        assertEquals("cluster-1", result.getCenter());// Municipality is ID
        assertEquals(municipalityName, result.getMunicipality());
        verify(repository).getMunicipalityToCluster6ByMunicipality(municipalityName);
    }

    @Test
    void testGetAllMunicipalityToCluster_returnsAll() {
        // Arrange
        Municipality munA = new Municipality("A", new Coordinates(1f, 2f));
        Municipality munB = new Municipality("B", new Coordinates(3f, 4f));
        List<MunicipalityToCluster> mockList = List.of(
                new MunicipalityToCluster(munA.getName(), "cluster-A"),
                new MunicipalityToCluster(munB.getName(), "cluster-B")
        );

        when(repository.findAll()).thenReturn(mockList);

        // Act
        List<MunicipalityToCluster> result = adapter.getAllMunicipalityToCluster();

        // Assert
        assertEquals(2, result.size());
        verify(repository).findAll();
    }
}