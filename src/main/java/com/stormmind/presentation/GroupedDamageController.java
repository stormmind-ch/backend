package com.stormmind.presentation;

import com.stormmind.infrastructure.services.persistence.GroupedDamageService;
import com.stormmind.presentation.dtos.response.AllGroupedDamageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {
        "https://stormmind.ch",
        "https://www.stormmind.ch"
})
@RequestMapping("/api/groupeddamage")
public class GroupedDamageController {

    private final GroupedDamageService groupedDamageService;

    public GroupedDamageController(GroupedDamageService groupedDamageService) {
        this.groupedDamageService = groupedDamageService;
    }

    @GetMapping("/all")
    public ResponseEntity<AllGroupedDamageDto> getAllDamages(){
        return ResponseEntity.ok().body(new AllGroupedDamageDto(groupedDamageService.getAll()));
    }

}
