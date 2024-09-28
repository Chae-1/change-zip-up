package com.kosa.chanzipup.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortFolioImageRepository extends JpaRepository<PortfolioImage, Long> {

    @Query("select pi from PortfolioImage pi  ")
    List<PortfolioImage> findAllByPortFolioId(Long id);

    @Modifying
    @Query("delete PortfolioImage pi " +
            " where pi.portfolio.id = :id")
    void deleteAllByPortfolioId(@Param("id") Long id);


}
