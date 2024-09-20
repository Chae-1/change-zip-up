package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.company.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    @EntityGraph(attributePaths = {"company", "estimateRequest"})
    @Query("SELECT e FROM Estimate e WHERE e.company = :company AND e.estimateStatus = 'WAITING'")
    List<Estimate> findAllWaitingByCompany(@Param("company") Company company);

    @Query("SELECT e FROM Estimate e join fetch e.estimateRequest request WHERE request.id = :estimateRequestId AND e.company = :company")
    Optional<Estimate> findByEstimateRequestIdAndCompany(@Param("estimateRequestId") Long estimateRequestId, @Param("company") Company company);


    @Query("select e from Estimate e join fetch e.company c " +
            "where e.estimateRequest.id = :estimateRequestId and e.company.id = :companyId")
    Optional<Estimate> findByEstimateRequestIdAndCompanyId(@Param("estimateRequestId") Long estimateRequestId, @Param("companyId") Long companyId);


    @Query("select e from Estimate e join fetch e.company c join e.estimateRequest r where c.email = :email and r.id = :estimateRequestId")
    Optional<Estimate> findByCompanyEmailAndEstimateRequestId(@Param("email") String email, @Param("estimateRequestId") Long estimateRequestId);

}
