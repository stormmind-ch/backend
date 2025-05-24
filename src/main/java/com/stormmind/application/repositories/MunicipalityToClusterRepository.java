package com.stormmind.application.repositories;

import com.stormmind.domain.MunicipalityToCluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityToClusterRepository extends JpaRepository<MunicipalityToCluster, String> {
    MunicipalityToCluster getMunicipalityToCluster6ByMunicipality(String municipality);
}
