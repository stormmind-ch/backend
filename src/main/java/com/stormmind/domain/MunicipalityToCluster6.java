package com.stormmind.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "municipality_to_cluster_6")
@Getter
public class MunicipalityToCluster6 {


    @Id
    @Column(name = "municipality")
    private String municipality;

    @Setter
    @Column(name = "center_municipality")
    private String center;


}

