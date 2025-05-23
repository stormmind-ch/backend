package com.stormmind.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Municipality {

    @Id
    private String name;

    @Embedded
    private Coordinates coordinates;

    public Municipality(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

}
