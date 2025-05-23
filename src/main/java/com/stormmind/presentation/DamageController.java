package com.stormmind.presentation;

import com.stormmind.domain.Damage;
import com.stormmind.infrastructure.services.persistence.DamageService;
import com.stormmind.presentation.dtos.response.AllDamagesDto;
import com.stormmind.presentation.dtos.response.DamageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/damage")
public class DamageController {

    private final DamageService damageService;

    public DamageController(DamageService damageService) {
        this.damageService = damageService;
    }

    @GetMapping("/alldamages")
    public ResponseEntity<AllDamagesDto> getAllEmployees(){
        return ResponseEntity.ok().body(new AllDamagesDto(damageService.getAllDamages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DamageDto> getDamagebyId(@PathVariable Long id){
        return ResponseEntity.ok().body(new DamageDto(damageService.getDamageById(id)));
    }


    @PostMapping("/")
    public ResponseEntity<Damage> saveEmployee(@RequestBody Damage damage)
    {
        return ResponseEntity.ok().body(damageService.saveDamage(damage));
    }

}
