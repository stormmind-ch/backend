package com.stormmind.infrastructure.persistence;

import com.stormmind.application.municipality.MunicipalityPort;
import com.stormmind.domain.Municipality;
import com.stormmind.infrastructure.repositories.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MunicipalityPersistenceAdapter implements MunicipalityPort {
    private final MunicipalityRepository municipalityRepository;

    @Override public List<Municipality> findAll(){
        return municipalityRepository.findAll();
    }
    @Override public Municipality findByName(String name) {
        return municipalityRepository.findById(name).orElse(null);
    }
}



