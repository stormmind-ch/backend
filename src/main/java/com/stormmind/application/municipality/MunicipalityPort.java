package com.stormmind.application.municipality;

import com.stormmind.domain.Municipality;

import java.util.List;

public interface MunicipalityPort {
    List<Municipality> findAll();
    Municipality findByName(String name);
}
