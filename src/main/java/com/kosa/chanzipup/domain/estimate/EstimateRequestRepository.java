package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("select er from EstimateRequest er where er.id = :estimateRequestId")
    Optional<EstimateRequest> findByIdWithUser(@Param("estimateRequestId")Long estimateRequestId);

    // 추가: 특정 유저의 가장 최근 견적 요청을 가져오는 쿼리
    Optional<EstimateRequest> findFirstByMemberOrderByRegDateDesc(Member member);
}