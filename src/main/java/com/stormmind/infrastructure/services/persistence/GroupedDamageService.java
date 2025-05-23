package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.repositories.GroupedDamageRepository;
import com.stormmind.domain.GroupedDamage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupedDamageService {

    private final GroupedDamageRepository groupedDamageRepository;

    public List<GroupedDamage> getAll() {
        return groupedDamageRepository.findAll();
    }

    public List<GroupedDamage> getByMunicipality(String municipality) {
        return groupedDamageRepository.findByMunicipality(municipality);
    }
}
