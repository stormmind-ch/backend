package com.stormmind.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "damages_grouped_count")
public class GroupedDamage implements Serializable {

    @Id
    @Column(name = "municipality")
    private String municipality;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "occurrence_count")
    private long occurrenceCount;

}
