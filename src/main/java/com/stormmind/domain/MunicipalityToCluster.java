package com.stormmind.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "municipality_to_cluster")
@Getter
public class MunicipalityToCluster {


    @Id
    @Column(name = "municipality")
    private String municipality;

    @Setter
    @Column(name = "center_municipality_6")
    private String center;

    // TODO: If there will be further models with differenct clusters, this entity will be extended.
}

