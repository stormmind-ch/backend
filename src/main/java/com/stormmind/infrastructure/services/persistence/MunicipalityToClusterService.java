package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.repositories.MunicipalityToClusterRepository;
import com.stormmind.domain.MunicipalityToCluster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipalityToClusterService {
    private final MunicipalityToClusterRepository municipalityToClusterRepository6;

    public List<MunicipalityToCluster> getAllMunicipalitiesToCluster(){
        return municipalityToClusterRepository6.findAll();
    }
    public MunicipalityToCluster getMunicipalityToClusterByMunicipality(String municipality){
        return municipalityToClusterRepository6.getMunicipalityToCluster6ByMunicipality(municipality);

    }
}
