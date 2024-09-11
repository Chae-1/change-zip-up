package com.kosa.chanzipup.domain.portfolio;

import com.kosa.chanzipup.domain.ConstructionType.ConstructionType;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
public class PortfolioConstructionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "construction_type_id")
    private ConstructionType constructionType;

}
