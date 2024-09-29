package com.kosa.chanzipup.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PortFolioImageRepository extends JpaRepository<PortfolioImage, Long> {
    @Query("delete from PortfolioImage i where i.portfolio.id = :portfolioId")
    @Modifying
    void deleteByPortfolioId(@Param("portfolioId") Long portfolioId);
}
