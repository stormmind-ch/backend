package com.stormmind.infrastructure.repositories;

import com.stormmind.domain.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MunicipalityRepository extends JpaRepository<Municipality, String> {
    Optional<Municipality> getMunicipalityByName(String name);
}
