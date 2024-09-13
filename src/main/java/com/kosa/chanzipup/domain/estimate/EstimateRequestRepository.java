package com.kosa.chanzipup.domain.estimate;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstimateRequestRepository extends JpaRepository<EstimateRequest, Long> {
    @EntityGraph(attributePaths = { "member" })
    @Query("select e from EstimateRequest e")
    List<EstimateRequest> findAllWithMember();
}