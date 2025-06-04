package com.stormmind.infrastructure.persistence;

import com.stormmind.domain.Damage;
import com.stormmind.infrastructure.repositories.DamageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DamagePersistenceAdapterTest {

    private DamageRepository damageRepository;
    private DamagePersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        damageRepository = mock(DamageRepository.class);
        adapter = new DamagePersistenceAdapter(damageRepository);
    }

    @Test
    void testGetAllDamages_returnsAll() {
        List<Damage> dummyList = List.of(new Damage(), new Damage());
        when(damageRepository.findAll()).thenReturn(dummyList);

        List<Damage> result = adapter.getAllDamages();

        assertEquals(2, result.size());
        verify(damageRepository).findAll();
    }

    @Test
    void testGetDamageById_found() {
        Damage damage = new Damage();
        damage.setId(1L);
        when(damageRepository.getDamageById(1L)).thenReturn(Optional.of(damage));

        Damage result = adapter.getDamageById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(damageRepository).getDamageById(1L);
    }

    @Test
    void testGetDamageById_notFound_returnsNull() {
        when(damageRepository.getDamageById(99L)).thenReturn(Optional.empty());

        Damage result = adapter.getDamageById(99L);

        assertNull(result);
        verify(damageRepository).getDamageById(99L);
    }

    @Test
    void testSaveDamage_setsTimestampsAndSaves() {
        Damage damage = new Damage();
        when(damageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Damage saved = adapter.saveDamage(damage);

        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        Duration diff = Duration.between(saved.getCreatedAt(), saved.getUpdatedAt());
        assertTrue(diff.abs().toMillis() <= 1,"Timestamps should be nearly identical");
                verify(damageRepository).save(damage);
    }

    @Test
    void testUpdateDamage_preservesCreatedAtAndUpdatesUpdatedAt() {
        Damage damage = new Damage();
        damage.setId(42L);

        LocalDateTime originalCreatedAt = LocalDateTime.of(2023, 1, 1, 12, 0);
        Damage existing = new Damage();
        existing.setCreatedAt(originalCreatedAt);

        when(damageRepository.getDamageById(42L)).thenReturn(Optional.of(existing));
        when(damageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Damage updated = adapter.updateDamage(damage);

        assertEquals(originalCreatedAt, updated.getCreatedAt());
        assertNotNull(updated.getUpdatedAt());
        assertTrue(updated.getUpdatedAt().isAfter(originalCreatedAt));
        verify(damageRepository).save(damage);
    }

    @Test
    void testDeleteDamageById_callsRepository() {
        adapter.deleteDamageById(123L);
        verify(damageRepository).deleteById(123L);
    }
}