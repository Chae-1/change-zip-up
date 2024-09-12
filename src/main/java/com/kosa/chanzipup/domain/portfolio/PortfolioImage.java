package com.kosa.chanzipup.domain.portfolio;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
public class PortfolioImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

}
