package com.stormmind.application.repositories;

import com.stormmind.domain.GroupedDamage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupedDamageRepository extends JpaRepository<GroupedDamage, String> {
    List<GroupedDamage> findByMunicipality(String municipality);
}
