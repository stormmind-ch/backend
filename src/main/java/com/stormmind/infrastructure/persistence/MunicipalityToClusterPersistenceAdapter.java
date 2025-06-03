package com.stormmind.infrastructure.persistence;

import com.stormmind.application.municipality.MunicipalityToClusterPort;
import com.stormmind.domain.MunicipalityToCluster;
import com.stormmind.infrastructure.repositories.MunicipalityToClusterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MunicipalityToClusterPersistenceAdapter implements MunicipalityToClusterPort {
    private final MunicipalityToClusterRepository municipalityToClusterRepository;

    @Override
    public MunicipalityToCluster findByMunicipality(String name) {
        return municipalityToClusterRepository.getMunicipalityToCluster6ByMunicipality(name);
    }

    @Override
    public List<MunicipalityToCluster> getAllMunicipalityToCluster() {
        return municipalityToClusterRepository.findAll();
    }
}
