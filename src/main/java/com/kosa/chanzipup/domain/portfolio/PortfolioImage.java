package com.kosa.chanzipup.domain.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public PortfolioImage(Portfolio portfolio, String imageSaveUrl) {
        this.portfolio = portfolio;
        this.imageUrl = imageSaveUrl;
    }

    public static PortfolioImage of(Portfolio portfolio, String imageSaveUrl) {
        return new PortfolioImage(portfolio, imageSaveUrl);
    }
}
