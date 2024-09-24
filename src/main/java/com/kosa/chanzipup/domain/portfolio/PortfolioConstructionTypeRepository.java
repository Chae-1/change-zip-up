package com.kosa.chanzipup.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioConstructionTypeRepository extends JpaRepository<PortfolioConstructionType, Long> {
    @Query("select type from PortfolioConstructionType type left join fetch type.constructionType c" +
            " left join fetch type.portfolio p where p.id in :portfolioIds")
    List<PortfolioConstructionType> findByPortfolioIdIn(@Param("portfolioIds") List<Long> portfolioIds);
}
