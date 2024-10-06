package com.kosa.chanzipup.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select p from Portfolio p "
            + "left join fetch p.buildingType b"
            + " left join fetch p.portfolioImages images")
    List<Portfolio> findAllWithImages();



    @Query("select p from Portfolio p "
            + "left join p.company c "
            + "left join fetch p.buildingType b "
            + "left join fetch p.portfolioImages images "
            + "where c.email = :email")
    List<Portfolio> findAllWithImagesByCompanyEmail(@Param("email") String email);


    @Query("select p from Portfolio p " +
            "left join p.company c " +
            "where c.email =:email and p.id = :portfolioId")
    Optional<Portfolio> findByIdAndCompanyEmail(Long portfolioId, String email);

    @Query("select p from Portfolio p " +
            "left join fetch p.company c " +
            "left join fetch p.buildingType b " +
            "where p.id = :portfolioId")
    Optional<Portfolio> findByIdWithCompany(@Param("portfolioId") Long portfolioId);
}
