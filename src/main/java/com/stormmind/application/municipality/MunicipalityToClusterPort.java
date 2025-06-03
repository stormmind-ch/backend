package com.stormmind.application.municipality;

import com.stormmind.domain.MunicipalityToCluster;

import java.util.List;

public interface MunicipalityToClusterPort {
    MunicipalityToCluster findByMunicipality(String name);
    List<MunicipalityToCluster> getAllMunicipalityToCluster();
}
