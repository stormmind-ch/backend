package com.stormmind.presentation;

import com.stormmind.infrastructure.persistence.GroupedDamagePersistenceAdapter;
import com.stormmind.presentation.dtos.response.groupedDamage.AllGroupedDamageDto;
import com.stormmind.presentation.dtos.response.groupedDamage.GroupedDamageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"*",
        "https://stormmind.ch",
        "https://www.stormmind.ch",
        "http://localhost:5173"
})
@RequestMapping("/api/groupeddamage")
public class GroupedDamageController {

    private final GroupedDamagePersistenceAdapter groupedDamagePersistenceAdapter;

    public GroupedDamageController(GroupedDamagePersistenceAdapter groupedDamagePersistenceAdapter) {
        this.groupedDamagePersistenceAdapter = groupedDamagePersistenceAdapter;
    }

    @GetMapping("/all")
    public ResponseEntity<AllGroupedDamageDto> getAllDamages(){
        List<GroupedDamageDto> damageDtos = groupedDamagePersistenceAdapter.getAll().stream()
                .map(GroupedDamageDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllGroupedDamageDto(damageDtos));
    }

}
