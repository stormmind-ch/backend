package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.persistence.MunicipalityToClusterRepository;
import com.stormmind.domain.Municipality;
import com.stormmind.domain.MunicipalityToCluster6;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipalityToClusterService {
    private final MunicipalityToClusterRepository municipalityToClusterRepository6;

    public List<MunicipalityToCluster6> getAllMunicipalitiesToCluster6(){
        return municipalityToClusterRepository6.findAll();
    }
    public MunicipalityToCluster6 getMunicipalityToClusterByMunicipality(String municipality){
        return municipalityToClusterRepository6.getMunicipalityToCluster6ByMunicipality(municipality);

    }
}
