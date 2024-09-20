package com.kosa.chanzipup.domain.estimate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public EstimatePrice(Estimate estimate, EstimateConstructionType constructionType, int price) {
        this.estimate = estimate;
        this.constructionType = constructionType;
        this.price = price;
    }
}
