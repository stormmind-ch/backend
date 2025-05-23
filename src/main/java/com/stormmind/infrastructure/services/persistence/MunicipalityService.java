package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.repositories.MunicipalityRepository;
import com.stormmind.domain.Municipality;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    public List<Municipality> getAllMunicipalities(){
        return municipalityRepository.findAll();
    }

    public Municipality getMunicipalityById(String id){
        Optional<Municipality> optionalMunicipality = municipalityRepository.getMunicipalitiesByName(id);
        return optionalMunicipality.orElse(null);
    }
}
