package com.stormmind.presentation;

import com.stormmind.domain.Damage;
import com.stormmind.infrastructure.persistence.DamagePersistenceAdapter;
import com.stormmind.presentation.dtos.response.damage.AllDamagesDto;
import com.stormmind.presentation.dtos.response.damage.DamageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {
        "https://stormmind.ch",
        "https://www.stormmind.ch",
        "http://localhost:5173"
})
@RequestMapping("/api/damage")
public class DamageController {

    private final DamagePersistenceAdapter damagePersistenceAdapter;

    public DamageController(DamagePersistenceAdapter damagePersistenceAdapter) {
        this.damagePersistenceAdapter = damagePersistenceAdapter;
    }

    @GetMapping("/alldamages")
    public ResponseEntity<AllDamagesDto> getAllDamages(){
        return ResponseEntity.ok().body(new AllDamagesDto(damagePersistenceAdapter.getAllDamages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DamageDto> getDamagebyId(@PathVariable Long id){
        return ResponseEntity.ok().body(new DamageDto(damagePersistenceAdapter.getDamageById(id)));
    }


    @PostMapping("/savedamage")
    public ResponseEntity<Damage> saveDamage(@RequestBody Damage damage)
    {
        return ResponseEntity.ok().body(damagePersistenceAdapter.saveDamage(damage));
    }

}
