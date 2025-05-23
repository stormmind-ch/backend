package com.stormmind.application.persistence;

import com.stormmind.domain.MunicipalityToCluster6;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityToClusterRepository extends JpaRepository<MunicipalityToCluster6, String> {
    MunicipalityToCluster6 getMunicipalityToCluster6ByMunicipality(String municipality);
}
