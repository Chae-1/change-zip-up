package com.kosa.chanzipup.domain.estimate;

import com.kosa.chanzipup.domain.account.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    @EntityGraph(attributePaths = "member")
    List<Estimate> findAllByMember(Member member);
}
