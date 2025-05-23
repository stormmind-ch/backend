package com.stormmind.infrastructure.services.persistence;

import com.stormmind.application.persistence.DamageRepository;
import com.stormmind.domain.Damage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class DamageService {

    private final DamageRepository damageRepository;

    public List<Damage> getAllDamages(){
        return damageRepository.findAll();
    }

    public Damage getDamageById(long id){
        Optional<Damage> damage = damageRepository.getDamageById(id);
        return damage.orElse(null);
    }

    public Damage saveDamage (Damage damage){
        damage.setCreatedAt(LocalDateTime.now());
        damage.setUpdatedAt(LocalDateTime.now());
        return damageRepository.save(damage);
    }

    public Damage updateDamage (Damage damage) {
        Optional<Damage> existingDamage = damageRepository.getDamageById(damage.getId());
        damage.setCreatedAt(existingDamage.get().getCreatedAt());
        damage.setUpdatedAt(LocalDateTime.now());
        return damageRepository.save(damage);
    }

    public void deleteDamageById (Long id) {
        damageRepository.deleteById(id);
    }
}
