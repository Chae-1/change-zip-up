package com.kosa.chanzipup.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioConstructionTypeRepository extends JpaRepository<PortfolioConstructionType, Long> {
    @Query("select pt from PortfolioConstructionType pt left join fetch pt.constructionType c" +
            " left join fetch pt.portfolio p where p.id in :portfolioIds")
    List<PortfolioConstructionType> findByPortfolioIdIn(@Param("portfolioIds") List<Long> portfolioIds);

    @Query(
            "select pt from PortfolioConstructionType pt " +
                    "left join fetch pt.constructionType ct " +
                    "left join pt.portfolio p " +
                    "where p.id =:portfolioId"
    )
    List<PortfolioConstructionType> findByPortfolioId(@Param("portfolioId") Long portfolioId);

    @Modifying
    @Query(
            "delete PortfolioConstructionType t " +
                    "where t.portfolio.id = :id"
    )
    void deleteAllByPortfolioId(@Param("id") Long id);
}
