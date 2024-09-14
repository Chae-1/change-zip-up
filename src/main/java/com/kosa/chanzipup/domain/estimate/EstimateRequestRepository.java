package com.kosa.chanzipup.domain.estimate;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EstimateRequestRepository extends JpaRepository<EstimateRequest, Long> {
    @EntityGraph(attributePaths = { "member" })
    @Query("select e from EstimateRequest e")
    List<EstimateRequest> findAllWithMember();

    // 날짜를 기반으로 EsimateRequest를 조회
    List<EstimateRequest> findAllByRegDate(LocalDate regDate);

    @EntityGraph(attributePaths = {"member"})
    @Query("select er from EstimateRequest er")
    Optional<EstimateRequest> findByIdWithUser(Long estimateRequestId);
}