package com.stormmind.infrastructure.persistence;

import com.stormmind.domain.GroupedDamage;
import com.stormmind.infrastructure.repositories.GroupedDamageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GroupedDamagePersistenceAdapterTest {

    private GroupedDamageRepository groupedDamageRepository;
    private GroupedDamagePersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        groupedDamageRepository = mock(GroupedDamageRepository.class);
        adapter = new GroupedDamagePersistenceAdapter(groupedDamageRepository);
    }

    @Test
    void testGetAll_returnsAllGroupedDamages() {
        // Arrange
        List<GroupedDamage> dummyList = List.of(new GroupedDamage(), new GroupedDamage());
        when(groupedDamageRepository.findAll()).thenReturn(dummyList);

        // Act
        List<GroupedDamage> result = adapter.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(groupedDamageRepository).findAll();
    }

    @Test
    void testGetByMunicipality_returnsCorrectGroupedDamages() {
        // Arrange
        String municipality = "TestTown";
        List<GroupedDamage> dummyList = List.of(new GroupedDamage());
        when(groupedDamageRepository.findByMunicipality(municipality)).thenReturn(dummyList);

        // Act
        List<GroupedDamage> result = adapter.getByMunicipality(municipality);

        // Assert
        assertEquals(1, result.size());
        verify(groupedDamageRepository).findByMunicipality(municipality);
    }
}