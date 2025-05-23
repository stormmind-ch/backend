package com.stormmind.application.repositories;

import com.stormmind.domain.Damage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DamageRepository extends JpaRepository<Damage, Long> {
    Optional<Damage> getDamageById(Long id);
}
