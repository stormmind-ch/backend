package com.stormmind.infrastructure.persistence;

import com.stormmind.infrastructure.repositories.GroupedDamageRepository;
import com.stormmind.domain.GroupedDamage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupedDamagePersistenceAdapter {

    private final GroupedDamageRepository groupedDamageRepository;

    public List<GroupedDamage> getAll() {
        return groupedDamageRepository.findAll();
    }

    public List<GroupedDamage> getByMunicipality(String municipality) {
        return groupedDamageRepository.findByMunicipality(municipality);
    }
}
