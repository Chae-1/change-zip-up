package com.kosa.chanzipup.domain.estimate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimatePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estimate_id")
    private Estimate estimate;


    @ManyToOne
    @JoinColumn(name = "ec_type_id")
    private EstimateConstructionType constructionType;

    private int price;
}
