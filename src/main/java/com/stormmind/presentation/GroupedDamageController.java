package com.stormmind.presentation;

import com.stormmind.infrastructure.services.persistence.GroupedDamageService;
import com.stormmind.presentation.dtos.response.AllGroupedDamageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groupeddamage")
public class GroupedDamageController {

    private final GroupedDamageService groupedDamageService;

    public GroupedDamageController(GroupedDamageService groupedDamageService) {
        this.groupedDamageService = groupedDamageService;
    }


    @GetMapping("/all")
    public ResponseEntity<AllGroupedDamageDto> getAllEmployees(){
        return ResponseEntity.ok().body(new AllGroupedDamageDto(groupedDamageService.getAll()));
    }

}
