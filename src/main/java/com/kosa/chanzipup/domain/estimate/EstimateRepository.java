package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.company.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    @EntityGraph(attributePaths = {"company", "estimateRequest"})
    @Query("SELECT e FROM Estimate e WHERE e.company = :company")
    List<Estimate> findAllByCompany(@Param("company") Company company);
}
