package com.kosa.chanzipup.domain.estimate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimatePriceRepository extends JpaRepository<EstimatePrice, Long> {

    @Query("delete from EstimatePrice p where p.estimate.id = :estimateId")
    @Modifying
    void deleteByEstimateId(@Param("estimateId") Long estimateId);
}
