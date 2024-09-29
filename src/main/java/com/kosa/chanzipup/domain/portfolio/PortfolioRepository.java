package com.kosa.chanzipup.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select p from Portfolio p left join fetch p.buildingType b left join fetch p.portfolioImages images")
    List<Portfolio> findAllWithImages();

    @Query("select p from Portfolio p left join fetch p.account a"
            +" left join fetch p.portfolioImages images"
            +" where p.id = :portfolioId and a.email = :userEmail")
    Optional<Portfolio> findByIdAndUserEmail(@Param("portfolioId") Long portfolioId, @Param("userEmail") String userEmail);
}
