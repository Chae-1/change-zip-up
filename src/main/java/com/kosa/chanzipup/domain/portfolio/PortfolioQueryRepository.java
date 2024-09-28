package com.kosa.chanzipup.domain.portfolio;

import com.kosa.chanzipup.domain.account.company.QCompany;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.kosa.chanzipup.domain.account.company.QCompany.company;
import static com.kosa.chanzipup.domain.portfolio.QPortfolio.portfolio;
import static com.kosa.chanzipup.domain.portfolio.QPortfolioImage.portfolioImage;

@Repository
@RequiredArgsConstructor
public class PortfolioQueryRepository {
    private final JPAQueryFactory factory;

    public Optional<Portfolio> findPortfolioWithAll(Long portfolioId, String email) {
        return Optional.of(
                factory.selectFrom(portfolio)
                        .leftJoin(portfolio.company, company)
                        .leftJoin(portfolio.buildingType).fetchJoin()
                        .leftJoin(portfolio.portfolioImages, portfolioImage).fetchJoin()
                        .where(portfolio.id.eq(portfolioId), company.email.eq(email))
                        .fetchOne()
        );
    }

}
