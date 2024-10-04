package com.kosa.chanzipup.domain.membership;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("select m from Membership m "
            + "left join fetch m.company c "
            + "left join fetch m.membershipType type "
            + "where c.email = :email "
            + "order by m.startDateTime desc"
    )
    List<Membership> findAllByUserEmailOrderByStartDateTimeDesc(@Param("email") String email);
}
