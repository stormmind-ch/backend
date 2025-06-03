package com.stormmind.infrastructure.services.persistence;

import com.stormmind.infrastructure.persistence.GroupedDamagePersistenceAdapter;
import com.stormmind.infrastructure.repositories.GroupedDamageRepository;
import com.stormmind.domain.GroupedDamage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GroupedDamagePersistenceAdapterTest {

    private GroupedDamageRepository groupedDamageRepository;
    private GroupedDamagePersistenceAdapter groupedDamagePersistenceAdapter;

    @BeforeEach
    void setUp() {
        groupedDamageRepository = mock(GroupedDamageRepository.class);
        groupedDamagePersistenceAdapter = new GroupedDamagePersistenceAdapter(groupedDamageRepository);
    }

    @Test
    void testGetAll() {
        List<GroupedDamage> mockList = Arrays.asList(new GroupedDamage(), new GroupedDamage());
        when(groupedDamageRepository.findAll()).thenReturn(mockList);

        List<GroupedDamage> result = groupedDamagePersistenceAdapter.getAll();

        assertEquals(2, result.size());
        verify(groupedDamageRepository).findAll();
    }

    @Test
    void testGetByMunicipality() {
        String municipality = "Berlin";
        List<GroupedDamage> mockList = List.of(new GroupedDamage());
        when(groupedDamageRepository.findByMunicipality(municipality)).thenReturn(mockList);

        List<GroupedDamage> result = groupedDamagePersistenceAdapter.getByMunicipality(municipality);

        assertEquals(1, result.size());
        verify(groupedDamageRepository).findByMunicipality(municipality);
    }
}