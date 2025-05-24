package com.stormmind.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "damages_grouped_count")
public class GroupedDamage implements Serializable {

    @Id
    private String municipality;

    private float latitude;

    private float longitude;

    private long occurrenceCount;

}
