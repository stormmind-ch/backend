package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.repositories.DamageRepository;
import com.stormmind.domain.Damage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DamageServiceTest {

    private DamageRepository damageRepository;
    private DamageService damageService;

    @BeforeEach
    void setup() {
        damageRepository = mock(DamageRepository.class);
        damageService = new DamageService(damageRepository);
    }

    @Test
    void testGetAllDamages() {
        List<Damage> mockList = Arrays.asList(new Damage(), new Damage());
        when(damageRepository.findAll()).thenReturn(mockList);

        List<Damage> result = damageService.getAllDamages();

        assertEquals(2, result.size());
        verify(damageRepository).findAll();
    }

    @Test
    void testGetDamageById_found() {
        Damage damage = new Damage();
        damage.setId(1L);

        when(damageRepository.getDamageById(1L)).thenReturn(Optional.of(damage));

        Damage result = damageService.getDamageById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(damageRepository).getDamageById(1L);
    }

    @Test
    void testGetDamageById_notFound() {
        when(damageRepository.getDamageById(99L)).thenReturn(Optional.empty());

        Damage result = damageService.getDamageById(99L);

        assertNull(result);
        verify(damageRepository).getDamageById(99L);
    }

    @Test
    void testSaveDamage() {
        Damage damage = new Damage();
        when(damageRepository.save(any(Damage.class))).thenReturn(damage);

        Damage result = damageService.saveDamage(damage);

        assertNotNull(result);
        assertNotNull(damage.getCreatedAt());
        assertNotNull(damage.getUpdatedAt());
        verify(damageRepository).save(damage);
    }

    @Test
    void testUpdateDamage() {
        Damage damage = new Damage();
        damage.setId(1L);

        Damage existing = new Damage();
        existing.setCreatedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
        when(damageRepository.getDamageById(1L)).thenReturn(Optional.of(existing));
        when(damageRepository.save(damage)).thenReturn(damage);

        Damage result = damageService.updateDamage(damage);

        assertNotNull(result);
        assertEquals(existing.getCreatedAt(), damage.getCreatedAt());
        assertNotNull(damage.getUpdatedAt());
        verify(damageRepository).getDamageById(1L);
        verify(damageRepository).save(damage);
    }

    @Test
    void testDeleteDamageById() {
        Long id = 1L;

        damageService.deleteDamageById(id);

        verify(damageRepository).deleteById(id);
    }
}