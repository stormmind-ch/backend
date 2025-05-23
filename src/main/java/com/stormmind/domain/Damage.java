package com.stormmind.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name="damage")

public class Damage {

    @Id
    @GeneratedValue
    private Long id;
    private float latitude;
    private float longitude;
    private String municipality;
    private LocalDateTime damageDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
